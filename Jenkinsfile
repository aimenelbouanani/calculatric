pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = "localhost:32780"
        IMAGE_NAME = "calculatric"
        IMAGE_TAG = "latest"  // Vous pouvez ajouter une version ou un tag dynamique si nécessaire
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Compilation') {
            steps {
                sh './gradlew compileJava'
            }
        }
        stage('Test unitaire') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Couverture de code') {
            steps {
                sh './gradlew jacocoTestReport'
                publishHTML(target: [
                    reportName: 'Rapport JaCoCo',
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html'
                ])
            }
        }
        stage('Analyse statique du code') {
            steps {
                sh './gradlew checkstyleMain'
                publishHTML(target: [
                    reportName: 'Checkstyle Report',
                    reportDir: 'build/reports/checkstyle',
                    reportFiles: 'main.html'
                ])
            }
        }
        stage('Package') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    // Vérifiez que Docker est disponible et prêt à être utilisé
                    sh 'docker --version'
                    sh 'docker info'

                    // Construire l'image Docker en utilisant le registre local
                    sh "docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }
        stage('Docker Push') {
            steps {
                script {
                    // Pousser l'image vers le registre local
                    sh "docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }
        stage('Déploiement sur Staging') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Déploiement en cours...'
                // Ajoutez ici les commandes pour déployer l'image Docker sur l'environnement de staging
                // Par exemple, en utilisant Docker, Kubernetes ou un autre outil de déploiement
                // sh "kubectl apply -f deployment.yml" (si vous utilisez Kubernetes)
            }
        }
        stage('Test d\'acceptation') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Tests d\'acceptation en cours...'
                // Exécution des tests d'acceptation après le déploiement
                sh './gradlew acceptanceTest -Dcalculatric.url=http://localhost:9090' // Assurez-vous que le port est correct
            }
        }
    }
    post {
        always {
            script {
                try {
                    // Nettoyage du conteneur Docker (si nécessaire)
                    sh "docker stop calculatric || true"
                    sh "docker rm calculatric || true"
                } catch (Exception e) {
                    echo "Aucun conteneur Docker à arrêter ou supprimer"
                }
            }
        }
    }
}

