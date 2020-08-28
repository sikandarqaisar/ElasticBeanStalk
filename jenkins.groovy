pipeline {
agent any
stages{
    stage('CheckoutModule1') {
        steps {
            dir("Module1")
            {
                git branch: "master",
                credentialsId: '6c0f262c-df9d-4146-a869-573d48901536',
                url: 'https://github.com/sikandarqaisar/ElasticBeanStalk.git'
            }
        }
    }

    stage('CheckoutModule2') {
        steps {
            dir("Module2")
            {
                git branch: "master",
                credentialsId: '6c0f262c-df9d-4146-a869-573d48901536',
                url: 'https://github.com/sikandarqaisar/ElasticBeanStalk.git'
            }
            sh 'ls'
            sh 'cd ..'
            sh 'cd Module1'
            sh 'ls'
            sh 'cd ..'
            sh 'cd ..'
            sh 'ls'
        }
    }
  }
}



pipeline {
agent any
stages{
    stage('Clone another repository to subdir') {
      steps {
        sh 'ls'
        sh 'mkdir subdir1'
        dir ('subdir1') {
            git branch: 'master',
            credentialsId: '4f722b6c-18b6-45eb-9c12-c60dea27578f',
            url: 'https://github.com/sikandarqaisar/ElasticBeanStalk.git'
        }
      }
    }
    stage('Clone another repository to masterDir') {
      steps {
        sh 'ls' 
        sh 'mkdir masterDir'
        dir ('masterDir') {
            git branch: 'master',
            credentialsId: '4f722b6c-18b6-45eb-9c12-c60dea27578f',
            url: 'https://github.com/sikandarqaisar/ElasticBeanStalk.git'
        }
      }
    }
  }
}






pipeline {
    agent any
    stages {
        stage('1st') {
            steps {
                git branch: 'any' 
                git 'https://github.com/sikandarqaisar/ElasticBeanStalk.git'
                sh 'git rev-parse --abbrev-ref HEAD'
                // sh 'git branch -r | awk \'{print $1}\' ORS=\'\\n\' >branches.txt'
                // cat branches.txt
            }
        }
        stage('2nd') {
            steps {
                git branch: 'any' 
                git 'https://github.com/sikandarqaisar/ElasticBeanStalk.git'
                sh "ls"
            }
        }
    }
}



pipeline {
    agent any
    stages {
        stage('1st') {
            steps {
                git 'https://github.com/sikandarqaisar/ElasticBeanStalk.git'
                sh 'git rev-parse --abbrev-ref HEAD'
                // sh 'git branch -r | awk \'{print $1}\' ORS=\'\\n\' >branches.txt'
                // cat branches.txt
            }
        }
        stage('2nd') {
            steps {
                git 'https://github.com/sikandarqaisar/ElasticBeanStalk.git'
                sh "ls"
            }
        }
    }
}
