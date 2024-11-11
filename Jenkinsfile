pipeline {
    agent any

    stages {
<<<<<<< HEAD
=======
        
>>>>>>> 4e61a13ee6c48588867f375f7c7f4e8c68c46604
        stage("Compilation") {
            steps {
                sh "./gradlew compileJava" 
            }
        }
<<<<<<< HEAD
        stage("Test unitaire") {
            steps {
=======
        stage("test unitaire"){
            steps{
>>>>>>> 4e61a13ee6c48588867f375f7c7f4e8c68c46604
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
<<<<<<< HEAD
            steps {
                sh "./gradlew checkstyleMain"
                publishHTML(target: [
                    reportDir: 'build/reports/checkstyle',
                    reportFiles: 'main.html',
                    reportName: "Checkstyle Report"
                ])
            }
        }
    }
}

=======
      steps {
           sh "./gradlew checkstyleMain"
           publishHTML (target: [
           reportDir: 'build/reports/checkstyle/',
           reportFiles: 'main.html',
           reportName: "Checkstyle Report"
      ])
           }
        }
    }
}
>>>>>>> 4e61a13ee6c48588867f375f7c7f4e8c68c46604
