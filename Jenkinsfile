pipeline {
    agent {
        docker {
            image 'clojure:openjdk-11-lein'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'lein do clean, uberjar'
            }
        }
        stage('Test') {
            steps {
                sh 'lein test'
            }
        }
    }
}