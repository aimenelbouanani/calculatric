pipeline {
    agent any
    triggers {
        pollSCM('H/5 ****')
    }
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
        stage("Code Coverage") {
            steps {
                // Génération du rapport JaCoCo
                sh "./gradlew jacocoTestReport"

                // Publication du rapport HTML dans Jenkins
                publishHTML(target: [
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html',
                    reportName: "JaCoCo Report"
                ])

                // Vérification de la couverture du code
                sh "./gradlew jacocoTestCoverageVerification"
            }
        }
        stage("Analyse statistique du code") {
            steps {
                sh "./gradlew checkstyleMain"
                publishHTML(target: [
                    reportDir: 'build/reports/checkstyle/',
                    reportFiles: 'main.html',
                    reportName: "Checkstyle Report"
                ])
            }
        }
    }
}

