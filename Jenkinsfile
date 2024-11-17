pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = "localhost:5001"    // Adresse du registre Docker local
        IMAGE_NAME = "calculatric"           // Nom de l'image Docker
        IMAGE_TAG = "latest"                 // Tag de l'image Docker
        APP_PORT = "8081"                    // Port utilisé par l'application à l'intérieur du conteneur
        EXPOSED_PORT = "9092"                // Port exposé sur la machine hôte
    }
    stages {
        stage('Checkout Code') {
            steps {
                echo "Récupération du code source depuis le dépôt Git."
                checkout scm // Vérifie automatiquement le code depuis le dépôt configuré
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Construction de l'image Docker."
                sh """
                    docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} .
                """
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "Envoi de l'image Docker vers le registre local."
                sh """
                    docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }

        stage('Déploiement sur Staging') {
            steps {
                echo "Déploiement de l'application sur l'environnement de staging."
                sh """
                    docker stop calculatric || true
                    docker rm calculatric || true
                    docker run -d --name calculatric -p ${EXPOSED_PORT}:${APP_PORT} ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }

        stage('Test d\'acceptation') {
            steps {
                echo "Exécution des tests d'acceptation."
                sh """
                    ./gradlew acceptanceTest -Dcalculatric.url=http://localhost:${EXPOSED_PORT}
                """
            }
        }
    }

    post {
        always {
            echo "Nettoyage des ressources après le pipeline."
            sh """
                docker stop calculatric || true
                docker rm calculatric || true
            """
        }
        success {
            echo "Pipeline terminé avec succès."
        }
        failure {
            echo "Échec du pipeline. Consultez les journaux pour résoudre les problèmes."
        }
    }
}

