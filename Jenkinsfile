pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = "localhost:5001"
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
                echo "Déploiement de l'application en cours sur l'environnement de staging."
                // Utiliser bash pour éviter des erreurs liées aux parenthèses ou caractères spéciaux
                sh """
                    #!/bin/bash
                    docker stop calculatric || true
                    docker rm calculatric || true
                    docker run -d --name calculatric -p 9090:9090 ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }
        stage('Test d\'acceptation') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Exécution des tests d'acceptation."
                sh './gradlew acceptanceTest -Dcalculatric.url=http://localhost:9090'
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

