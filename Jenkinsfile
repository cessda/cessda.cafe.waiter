pipeline {
    options {
        ansiColor('xterm')
        buildDiscarder logRotator(artifactNumToKeepStr: '5', numToKeepStr: '10')
    }

    environment {
        product_name = "cafe"
        module_name = "waiter"
        image_tag = "${docker_repo}/${product_name}-${module_name}:${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
    }

    agent any

    stages {
        // Building on master
		stage('Build Project') {
			steps {
                withMaven {
                    sh 'mvn clean install'
				}
			}
            when { branch 'master' }
		}
        // Not running on master - test only (for PRs and integration branches)
		stage('Test Project') {
			steps {
                withMaven {
                    sh 'mvn clean test'
				}
			}
            when { not { branch 'master' } }
		}
		stage('Record Issues') {
			steps {
				recordIssues(tools: [java()])
			}
		}
        stage('Run Sonar Scan') {
            steps {
                withSonarQubeEnv('cessda-sonar') {
                    withMaven {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
            when { branch 'master' }
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
                    sh "mvn jib:build -Djib.console=plain -Dimage=${image_tag}"
                }
                sh("gcloud container images add-tag ${IMAGE_TAG} ${docker_repo}/${product_name}-${module_name}:${env.BRANCH_NAME}-latest")
            }
            when { branch 'master' }
        }
        /*stage('Deploy Docker Image') {
            steps {
                build job: '../cessda.cafe.deployment/master', parameters: [string(name: 'waiter_image_tag', value: "${image_tag}"), string(name: 'module', value: 'waiter')], wait: false
            }
            when { branch 'master' }
        }*/
    }
}
