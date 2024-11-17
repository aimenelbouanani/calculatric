pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = "localhost:5001"
        IMAGE_NAME = "calculatric"
        IMAGE_TAG = "latest"
    }
    stages {
        // Étapes précédentes inchangées...

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
                    docker run -d --name calculatric -p 9091:9090 ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }
        stage('Test d\'acceptation') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Exécution des tests d'acceptation."
                sh './gradlew acceptanceTest -Dcalculatric.url=http://localhost:9091'
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

