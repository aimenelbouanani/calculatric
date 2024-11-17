pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = "localhost:5001" // Correspond au registre actuel
        IMAGE_NAME = "calculatric"
        IMAGE_TAG = "latest"
    }
    stages {
        stage('Checkout') {
            steps {
                echo "Étape de récupération du code source."
                checkout scm
            }
        }
        stage('Compilation') {
            steps {
                echo "Étape de compilation en cours."
                sh './gradlew compileJava'
            }
        }
        stage('Test unitaire') {
            steps {
                echo "Exécution des tests unitaires."
                sh './gradlew test'
            }
        }
        stage('Couverture de code') {
            steps {
                echo "Génération du rapport de couverture de code avec JaCoCo."
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
                echo "Exécution de l'analyse statique du code."
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
                echo "Création du package de l'application."
                sh './gradlew build'
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    echo "Vérification de Docker."
                    sh 'docker --version'
                    sh 'docker info'
                    
                    echo "Construction de l'image Docker."
                    sh "docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }
        stage('Docker Push') {
            steps {
                script {
                    echo "Poussée de l'image vers le registre Docker."
                    sh "docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }
        stage('Déploiement sur Staging') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                script {
                    echo "Déploiement de l'application en cours sur l'environnement de staging."
                    def freePort = sh(script: """
                        comm -23 <(seq 9000 9100) <(ss -tan | awk '{print \$4}' | grep -o '[0-9]*\$' | sort -n) | head -n 1
                    """, returnStdout: true).trim()
                    
                    sh """
                        docker stop calculatric || true
                        docker rm calculatric || true
                        docker run -d --name calculatric -p ${freePort}:9090 ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
                    """
                    env.STAGING_PORT = freePort
                    echo "Application déployée sur le port dynamique ${STAGING_PORT}."
                }
            }
        }
        stage('Test d\'acceptation') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Exécution des tests d'acceptation."
                sh "./gradlew acceptanceTest -Dcalculatric.url=http://localhost:${STAGING_PORT}"
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

