pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK21'
    }

    environment {
        DOCKER_IMAGE = "hassanlahlami1/refactored-finance-app"
        DOCKER_TAG   = "${BUILD_NUMBER}"
        GIT_MAIN     = "main"
        GIT_TEST     = "test"
    }

    stages {

        stage('Checkout (test branch)') {
            steps {
                checkout scm
                sh 'git branch --show-current'
            }
        }

        stage('Build & Unit Tests') {
            steps {
                sh 'mvn clean verify'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Coverage - JaCoCo') {
            steps {
                sh 'mvn jacoco:report'
            }
            post {
                always {
                    publishHTML(target: [
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'JaCoCo Coverage'
                    ])
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Merge test → main') {
            when {
                branch 'test'
            }
            steps {
                sh """
                git checkout ${GIT_MAIN}
                git pull origin ${GIT_MAIN}
                git merge ${GIT_TEST}
                git push origin ${GIT_MAIN}
                """
            }
        }

        stage('Build Docker Image') {
            when {
                branch 'test'
            }
            steps {
                sh """
                docker build -t $DOCKER_IMAGE:$DOCKER_TAG .
                docker tag $DOCKER_IMAGE:$DOCKER_TAG $DOCKER_IMAGE:latest
                """
            }
        }

        stage('Push Docker Image to Docker Hub') {
            when {
                branch 'test'
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'Docker-secret',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    docker push $DOCKER_IMAGE:$DOCKER_TAG
                    docker push $DOCKER_IMAGE:latest
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Tests validés → merge main + image Docker pushée'
        }
        failure {
            echo 'Pipeline arrêté (tests / qualité non validés)'
        }
    }
}
