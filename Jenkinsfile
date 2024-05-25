pipeline {
    agent none
    environment {
        CI_REGISTRY = 'nexus.xuxiaowei.com.cn:48501'
        GROUP_ID = 'cn.com.xuxiaowei.ip'
        ARTIFACT_ID = 'ip'
        VERSION = '0.0.1-SNAPSHOT'
        CI_JAVA_OPTS = '-Xms256m -Xmx512m'
    }
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
                    args '--user 0 -e DOCKER_HOST=tcp://172.25.25.7:2375'
                }
            }
            steps {
                sh 'env'
                sh 'ls -la'
                sh 'docker info'
                withCredentials([string(credentialsId: 'CI_REGISTRY_USER', variable: 'CI_REGISTRY_USER'), string(credentialsId: 'CI_REGISTRY_PASSWORD', variable: 'CI_REGISTRY_PASSWORD')]) {
                    sh 'env'
                    sh 'docker login -u "$CI_REGISTRY_USER" -p "$CI_REGISTRY_PASSWORD" $CI_REGISTRY'
                }
                sh 'docker build -t $CI_REGISTRY/$GROUP_ID/$ARTIFACT_ID:$VERSION-$BUILD_ID . --build-arg GROUP_ID=$GROUP_ID --build-arg ARTIFACT_ID=$ARTIFACT_ID --build-arg VERSION=$VERSION --build-arg CI_PIPELINE_URL=$BUILD_URL --build-arg CI_JOB_ID=$BUILD_ID --build-arg JAVA_OPTS="$CI_JAVA_OPTS" --build-arg APP_ARGS="$APP_ARGS"'
                sh 'docker push $CI_REGISTRY/$GROUP_ID/$ARTIFACT_ID:$VERSION-$BUILD_ID'
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
