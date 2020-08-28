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
                git url:'https://github.com/sikandarqaisar/ElasticBeanStalk.git'
                sh 'git rev-parse --abbrev-ref HEAD'
            }
        }
    //     stage('2nd') {
    //         steps {
    //             checkout([$class: 'GitSCM',
    //                 branches: [[name: 'any']],
    //                 userRemoteConfigs: [[url: 'https://github.com/sikandarqaisar/ElasticBeanStalk.git']]
    //             ])
    //         }
    //     }
    }
}



pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps{
                checkout([  
                            $class: 'GitSCM', 
                            branches: [[name: '']], 
                            doGenerateSubmoduleConfigurations: false, 
                            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'CalibrationResults']], 
                            submoduleCfg: [], 
                            userRemoteConfigs: [[url: 'https://github.com/sikandarqaisar/ElasticBeanStalk.git']]
                        ])
                sh 'git rev-parse --abbrev-ref HEAD'
                checkout([  
                            $class: 'GitSCM', 
                            branches: [[name: 'refs/heads/master']], 
                            doGenerateSubmoduleConfigurations: false, 
                            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'Combination']], 
                            submoduleCfg: [], 
                            userRemoteConfigs: [[url: 'https://github.com/sikandarqaisar/ElasticBeanStalk.git']]
                        ])                
                sh 'git rev-parse --abbrev-ref HEAD'
            }
        }
        stage('2nd') {
            steps {
                sh "ls"
            }
        }
    }
}
