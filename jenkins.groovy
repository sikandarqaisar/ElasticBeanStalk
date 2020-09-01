pipeline {
    agent any
    parameters {
    string(defaultValue: "master", name: 'HelmChart')
    string(defaultValue: "master", name: 'ElasticBeanStalk')
    }
    stages {
        stage('Trigger ElasticBeanStalk Repo') {
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
                        def ElasticBeanStalk = sh(script: "git name-rev --name-only HEAD | cut -b 16- | tr -d '[:space:]'", returnStdout: true)
                        println("branch_name = ${ElasticBeanStalk}")                        
                        echo "Triggering ElasticBeanStalk job"
                        build job: "1stRepo", wait: false, parameters: [[$class: 'StringParameterValue', name: 'branch_name', value: ElasticBeanStalk]]
                    }
                }
            }
        }
        stage('Trigger Helm chart Repo') {
            steps {
                script {
                    echo "Triggering Helm-Chart job"
                    println(HelmChart)
                    build job: "2ndRepo", wait: false, parameters: [[$class: 'StringParameterValue', name: 'branch_name', value: HelmChart]]
                }
            }
        }
    }
}
