pipeline {
    agent none
    stages {
        stage('Build-Maven') {
            agent {
                docker {
                    image 'maven:3.6.3-openjdk-17'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'env'
                sh 'mvn --version'
                sh 'ls -la'
                sh 'mvn clean package -U -s settings.xml'
            }
        }
        stage('Build-Docker') {
            agent {
                docker {
                    image 'docker:26.1.3-cli'
                    args '-e DOCKER_HOST=tcp://172.25.25.7:2375'
                }
            }
            steps {
                sh 'env'
                sh 'ls -la'
                sh 'docker info'
            }
        }
        stage('Publish-Docker') {
            agent {
                docker {
                    image 'docker:26.1.3-cli'
                    args '-e DOCKER_HOST=tcp://172.25.25.7:2375'
                }
            }
            steps {
                sh 'env'
                sh 'docker info'
            }
        }
    }
}
