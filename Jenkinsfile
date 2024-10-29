pipeline {
    agent any

    stages {
        
        
        stage("Compilation") {
            steps {
                // Compilation du code avec Gradle
                sh './gradlew compileJava' // Assurez-vous qu'un script Gradle est présent et exécutable
            }
        }
        stage("Test Unitaire") {
            steps {
                // Exécution des tests unitaires avec Gradle
                sh './gradlew test'
            }
        }
    }
}

