pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = "localhost:5001"
        IMAGE_NAME = "calculatric"
        IMAGE_TAG = "latest"
    }
    stages {
        stage('Build de l\'image Docker') {
            steps {
                script {
                    echo "Construction de l'image Docker avec les nouveaux ports."
                    sh """
                        # Build de l'image avec les nouveaux ports
                        docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} .
                    """
                }
            }
        }

        stage('Push vers Docker Registry') {
            steps {
                script {
                    echo "Pousser l'image vers le Docker Registry."
                    sh """
                        docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
                    """
                }
            }
        }

        stage('Déploiement sur Staging') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Déploiement de l'application en cours sur l'environnement de staging."
                sh """
                    #!/bin/bash
                    docker stop calculatric || true
                    docker rm calculatric || true
                    docker run -d --name calculatric -p 6000:6001 ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }

        stage('Test d\'acceptation') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Exécution des tests d'acceptation."
                sh './gradlew acceptanceTest -Dcalculatric.url=http://localhost:6000'
            }
        }
    }

    post {
        always {
            script {
                echo "Nettoyage des conteneurs Docker."
                try {
                    sh "docker stop calculatric || true"
                    sh "docker rm calculatric || true"
                } catch (Exception e) {
                    echo "Aucun conteneur Docker à nettoyer."
                }
            }
        }
    }
}

