pipeline {
    agent none
    stages {
        stage('Build-Maven') {
            agent {
                docker { image 'maven:3.6.3-openjdk-17' }
            }
            steps {
                sh 'env'
                sh 'mvn --version'
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
