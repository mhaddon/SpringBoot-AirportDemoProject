pipeline {
    agent any

    stages {
        stage('Build') {
            steps{
                echo 'Compiling...'
                withMaven (maven: 'myMaven') {
                    sh 'mvn clean compile'
                }
            }

        }
        stage('Test') {
            steps {
                echo 'Testing...'
                withMaven (maven: 'myMaven') {
                    sh 'mvn clean test'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
                withMaven (maven: 'myMaven') {
                    sh 'mvn clean package'
                }
            }
        }
    }
}