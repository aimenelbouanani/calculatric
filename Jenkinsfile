pipeline {
    agent any
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
        stage('Docker build') {
            steps {
                sh "docker build -t localhost:5000/calculatric ."
            }
        }
        stage('Docker push') {
            steps {
                sh "docker push localhost:5000/calculatric"
            }
        }
        stage('Déploiement sur staging') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Déploiement en cours...'
                // Ajoutez ici les commandes pour le déploiement en staging
            }
        }
        stage('Test d\'acceptation') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Tests d\'acceptation en cours...'
                // Ajoutez ici les tests d'acceptation
            }
        }
    }
    post {
        always {
            script {
                try {
                    sh "docker stop calculatric"
                    sh "docker rm calculatric"
                } catch (Exception e) {
                    echo "Aucun conteneur Docker à arrêter"
                }
            }
        }
    }
}

