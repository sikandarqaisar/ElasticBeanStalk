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
                        def BranchName1stRepo = sh(script: "git name-rev --name-only HEAD | cut -b 16-", returnStdout: true)
                        println("branch_name = ${BranchName1stRepo}")
                    }
                }
                script {
                        echo "Triggering 1st job"
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
                dir("ElasticBeanStalk_Repo2"){
                    script {
                        def BranchName2ndRepo = sh(script: "git name-rev --name-only HEAD | cut -b 16-", returnStdout: true)
                        println("branch_name = ${BranchName2ndRepo}")
                    }
                }
                script {
                        echo "Triggering 2nd job"
                        build job: "2ndRepo", wait: false, parameters: [string(name: 'branch_name', value: String.valueOf(BranchName2ndRepo))]
                }
            }
        }
    }
}
