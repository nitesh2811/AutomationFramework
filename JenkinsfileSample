pipeline {
    agent any

    stages {
        stage('Build the Project') {
            steps {
                echo 'Build Project'
            }
        }
    
         stage('RUN UT') {
            steps {
                echo 'Run unit test cases'
            }
        }
        
            stage('Depoly to dev') {
            steps {
                echo 'Deploy to Dev'
            }
        }
        
             stage('Depoly to QA') {
            steps {
                echo 'Deploy to QA'
            }
        }
   
             stage('Run Automation Test cases') {
            steps {
                echo 'Automation Test cases'
            }
        }   
    
                stage('Run Automation Regression Test cases') {
            steps {
                echo 'Automation Regression Test cases'
            }
        }  
        
                  stage('Deploy to Prod') {
            steps {
                echo 'Deploy to Prod'
            }
        }
        
        
        
    }
}