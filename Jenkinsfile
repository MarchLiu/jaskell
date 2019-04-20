pipeline {
    agent {
        docker {
            image 'marsliu/clojure-devel-studio:jdk-11'
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
                sh 'lein pom'
                sh 'mvn -Dmaven.compiler.target=1.8 -Dmaven.compiler.source=1.8 test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}
