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
                sh 'lein do clean, compile'
            }
        }
        stage('Test') {
            steps {
                sh 'lein test'
            }
            post {
                always {
                    junit 'target/test-reports/*.xml'
                }
            }
        }
    }
}