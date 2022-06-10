/*
 * Copyright CESSDA ERIC 2022.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

pipeline {
    options {
        ansiColor('xterm')
        buildDiscarder logRotator(artifactNumToKeepStr: '5', numToKeepStr: '10')
        disableConcurrentBuilds abortPrevious: true
    }

    environment {
        product_name = "cafe"
        module_name = "waiter"
        image_tag = "${docker_repo}/${product_name}-${module_name}:${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
    }

    agent any

    stages {
        // Building on master
        stage('Pull SDK Docker Image') {
            agent {
                docker {
                    image 'openjdk:17-jdk'
                    reuseNode true
                }
            }
            stages {
                stage('Build Project') {
                    steps {
                        withMaven {
                            sh './mvnw clean install'
                        }
                    }
                    when { branch 'master' }
                }
                // Not running on master - test only (for PRs and integration branches)
                stage('Test Project') {
                    steps {
                        withMaven {
                            sh './mvnw clean test'
                        }
                    }
                    when { not { branch 'master' } }
                }
                stage('Record Issues') {
                    steps {
                        recordIssues aggregatingResults: true, tools: [errorProne(), java()]
                    }
                }
                stage('Run Sonar Scan') {
                    steps {
                        withSonarQubeEnv('cessda-sonar') {
                            withMaven {
                                sh './mvnw sonar:sonar'
                            }
                        }
                    }
                    when { branch 'master' }
                }
            }
        }
        stage("Get Sonar Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: false
                }
            }
            when { branch 'master' }
        }
        stage('Build and Push Docker Image') {
            steps {
                sh 'gcloud auth configure-docker'
                withMaven {
                    sh "./mvnw jib:build -Dimage=${image_tag}"
                }
                sh("gcloud container images add-tag ${IMAGE_TAG} ${docker_repo}/${product_name}-${module_name}:${env.BRANCH_NAME}-latest")
            }
            when { branch 'master' }
        }
        stage('Deploy Docker Image') {
            steps {
                build job: '../cessda.cafe.deployment/master', parameters: [string(name: 'image_tag', value: "${env.BRANCH_NAME}-${env.BUILD_NUMBER}"), string(name: 'component', value: 'waiter')], wait: false
            }
            when { branch 'master' }
        }
    }
}
