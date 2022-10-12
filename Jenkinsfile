pipeline {
    agent any
    environment {
        REPO_NAME = scm.getUserRemoteConfigs()[0].getUrl().tokenize('/').last().split("\\.git")[0].toLowerCase()
        DOCKER_IMAGE_BASE_NAME = "johnburnsdev/etf-${REPO_NAME}"
        DOCKER_IMAGE_NAME = "${DOCKER_IMAGE_BASE_NAME}:${TAG_NAME}"
        DOCKER_IMAGE_NAME_LATEST = "${DOCKER_IMAGE_BASE_NAME}:latest"
        DOCKERHUB = credentials('DOCKERHUB')
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo PATH = ${PATH}
                    echo M2_HOME = ${M2_HOME}
                '''
            }
        }
        stage ('Test') {
            steps {
                sh 'mvn clean compile test'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
        stage ('Release and Reversion') {
            when {
                allOf {
                    branch 'main';
                    not {
                        changelog '.*\\[maven-release-plugin\\].*'
                    }
                }
            }
            steps {
                sh '''
                    echo Non-release commit detected on main branch. Creating new release and tag.
                    mvn -B release:prepare
                    mvn release:perform
                    mvn release:clean
                '''
            }
        }
        stage('Generate Docker Image') {
            when {
              buildingTag()
            }
            steps {
                sh '''
                    echo Generating jar file...
                    mvn -DskipTests=true package
                    echo Building tag "$TAG_NAME" for "$REPO_NAME"...
                    cp "$EAT_THE_FROG_DIRECTORY"/Dockerfile ./Dockerfile
                    docker build -t "$DOCKER_IMAGE_NAME" -t "$DOCKER_IMAGE_NAME_LATEST" .
                    echo "$DOCKERHUB_PSW" | docker login --username="$DOCKERHUB_USR" --password-stdin
                    docker push "$DOCKER_IMAGE_NAME"
                    docker push "$DOCKER_IMAGE_NAME_LATEST"
                '''
            }
        }
        stage('Deploy') {
            when {
                buildingTag()
            }
            steps {
                script {
                    try {
                        sh '''
                            echo Stopping previous deployment of "$REPO_NAME"
                            echo "$DOCKERHUB_PSW" | docker login --username="$DOCKERHUB_USR" --password-stdin
                            docker container stop "$REPO_NAME"
                            docker container rm "$REPO_NAME"
                        '''
                    }
                    finally {
                        sh '''
                            echo Deploying version "$TAG_NAME" of "$REPO_NAME"
                            cp "$EAT_THE_FROG_DIRECTORY"/docker-compose.yaml ./docker-compose.yaml
                            cp "$EAT_THE_FROG_DIRECTORY"/.env ./.env
                            docker-compose up -d "$REPO_NAME"
                        '''
                    }
                }
            }
        }
        stage ('Clean up') {
            steps {
                cleanWs()
            }
        }
    }
}