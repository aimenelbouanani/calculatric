pipeline {
    agent any

    stages {
        
        stage("Compilation") {
            steps {
                sh "./gradlew compileJava" 
            }
        }
        
        stage("Test unitaire") {
            steps {
                sh "./gradlew test"
            }
        }
        
        stage("Couverture de code") {
            steps {
                sh "./gradlew jacocoTestReport"
                publishHTML(target: [
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html',
                    reportName: "Rapport JaCoCo"
                ])
                sh "./gradlew jacocoTestCoverageVerification"
            }
        }
        
        stage("Analyse statique du code") {
            steps {
                sh "./gradlew checkstyleMain"
                publishHTML(target: [
                    reportDir: 'build/reports/checkstyle/',
                    reportFiles: 'main.html',
                    reportName: "Checkstyle Report"
                ])
            }
        }
        
        stage("Package") {
            steps {
                sh "./gradlew build"
            }
        }
        
        stage("Docker build") {
            steps {
                sh "docker build -t calculatric ."
            }
        }
        
        stage("Docker push") {
            steps {
                sh "docker push localhost:5000/calculatric"
            }
        }
        
        stage("Déploiement sur staging") {
            steps {
                sh "docker run -d --rm -p 8765:8080 --name calculatric localhost:5000/calculatric"
            }
        }
        
        stage("Test d'acceptation") {
            steps {
                sleep 60
                sh "chmod +x acceptance_test.sh && ./acceptance_test.sh"
            }
        }
    }

    post {
        always {
            sh "docker stop calculatric"
        }
    }
}

