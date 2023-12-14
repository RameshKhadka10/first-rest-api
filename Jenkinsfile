pipeline {
    agent any
    tools {
        maven 'MAVEN_HOME'
    }
    stages {
        stage('Build Maven') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/RameshKhadka10/first-rest-api.git']])
                sh 'mvn clean install'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t rameshkhadka/first-rest-api .'
                }
            }
        }
        stage('Push Docker Image to Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerHubPwd', variable: 'dockerHubPwd')]) {
                    sh 'docker login -u rameshkhadka -p ${dockerHubPwd}'
}
                    sh 'docker push rameshkhadka/first-rest-api'
                }
            }
        }
    }
}