pipeline {
    agent any
    parameters {
    string(defaultValue: "master", name: 'BranchName1stRepo')
    string(defaultValue: "master", name: 'BranchName2ndRepo')
    }    
    stages {
        stage('Checkout 1st Repo') {
            steps{
                checkout([  
                            $class: 'GitSCM', 
                            branches: [[name: '']], 
                            doGenerateSubmoduleConfigurations: false, 
                            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'ElasticBeanStalk_Repo']], 
                            submoduleCfg: [], 
                            userRemoteConfigs: [[url: 'https://github.com/sikandarqaisar/ElasticBeanStalk.git']]
                        ])
                dir("ElasticBeanStalk_Repo"){
                    script {
                        sh 'git name-rev --name-only HEAD'
                    }
                }
                script {
                        echo "Triggering 1st job"
                        FULL_PATH_BRANCH = 'git name-rev --name-only HEAD'
                        BranchName1stRepo = FULL_PATH_BRANCH.substring(FULL_PATH_BRANCH.lastIndexOf('/') + 1, FULL_PATH_BRANCH.length())
                        echo BranchName1stRepo
                        build job: "1stRepo", wait: false, parameters: [string(name: 'branch_name', value: String.valueOf(BranchName1stRepo))]
                }                        
            }
        }
        stage('Checkout 2nd Repo') {
            steps {
                checkout([  
                            $class: 'GitSCM', 
                            branches: [[name: '']], 
                            doGenerateSubmoduleConfigurations: false, 
                            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'ElasticBeanStalk_Repo2']], 
                            submoduleCfg: [], 
                            userRemoteConfigs: [[url: 'https://github.com/sikandarqaisar/ElasticBeanStalk.git']]
                        ])
                script {
                        echo "Triggering 2nd job"
                        build job: "2ndRepo", wait: false, parameters: [string(name: 'branch_name', value: String.valueOf(BranchName2ndRepo))]
                }
            }
        }
    }
}
