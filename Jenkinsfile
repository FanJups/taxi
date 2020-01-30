pipeline{
    agent any
    stages{
        
        stage('COMPILE') {
            steps{
                script{
                    last_started = env.STAGE_NAME;
                    
                    sh "mvn clean install -Dmaven.test.skip=true";
                    
                    echo "$last_started Passed successfully!!";
                }
                
            }
        }
        
        stage('JUNIT:JaCoCo') {
            steps{
                script {
                    last_started = env.STAGE_NAME;
                    sh "mvn test org.jacoco:jacoco-maven-plugin:prepare-agent -Dmaven.test.failure.ignore-false";
                    echo "$last_started Passed successfully!!";
                }
                
            }
        }
        
        
        stage('SonarQube Analysis') {
            steps{
                script{
                    withSonarQubeEnv('SonarQube') {
                        last_started = env.STAGE_NAME;
                        sh 'mvn sonar:sonar';
                        echo "$last_started Passed successfully!!";
                    }
                }
                
            }
        }
        
        stage('Quality Gate Status Check') {
            steps{
                script{
                    last_started = env.STAGE_NAME;
                    
                    timeout(time: 1, unit: 'HOURS'){
                        def qg = waitForQualityGate()
                        if( qg.status != 'OK'){
                            error "Pipeline aborted due to Quality Gate failure: ${qg.status}"
                        }
                    }
                    
                    echo "$last_started Passed successfully!!";
                    
                }
                
            }
        }
        
        
        stage('Arrêt du déploiement précédent') {
            steps{
                script{
                    last_started = env.STAGE_NAME;
                    
                     try {
                            sh script:"sudo fuser -k -n tcp 81 >> tmp";
                     } catch (error) {}
                     
                    echo "$last_started Passed successfully!!";
                    
                }
                
            }
        }
        
        
        stage('Déploiement') {
            steps{
                script{
                    last_started = env.STAGE_NAME;
                    
                    echo "$last_started : " +currentBuild.currentResult;

                    emailext attachLog: true,
                    mimeType: 'text/html',
                    to: 'mimba.ngouana@advance-it-group.biz,if5.forge@advance-it-group.biz,merlin.saha@advance-it-group.biz,daniel.kamga@advance-it-group.biz,yves.bakota@advance-it-group.biz,fanon.jupkwo@advance-it-group.biz',
                    subject: last_started +"  :  "+ currentBuild.currentResult +"#$BUILD_NUMBER",
                    body: "En pièce-jointe le fichier des logs de ${env.JOB_NAME}#$BUILD_NUMBER ";
                    
                    sh script: "cd ${env.WORKSPACE}/target && sudo java -jar taxiride-1.0.1.jar --server.port=81";
                    echo "$last_started Passed successfully!!";
                    
                }
                
            }
        }
    }
    
    post {
        failure {
            echo "$last_started : " +currentBuild.currentResult;
            
            emailext attachLog: true,
            mimeType: 'text/html',
            to: 'mimba.ngouana@advance-it-group.biz,if5.forge@advance-it-group.biz,daniel.kamga@advance-it-group.biz,yves.bakota@advance-it-group.biz,fanon.jupkwo@advance-it-group.biz',
            subject: last_started +"  :  "+ currentBuild.currentResult +"# $BUILD_NUMBER",
            body: "En pièce-jointe le fichier des logs de ${env.JOB_NAME}#$BUILD_NUMBER ";
            //body: '${FILE, path="/var/lib/jenkins/jobs/$JOB_NAME/builds/$BUILD_NUMBER/log"}' , replyTo: 'if5.forge@advance-it-group.biz',
            //sh script: "echo if5-forge | sudo -S sleep 1 && sudo su - if5-forge;mail -s 'Test'  baounabaouna@gmail.com << /dev/null";
            //mail bcc: '', body: 'Test pipeline', cc: '', from: 'if5.forge@advance-it-group.biz', replyTo: 'if5.forge@advance-it-group.biz', subject: 'Test pipeline', to: 'baounabaouna@gmail.com';
        }
        success{
            echo "$last_started : " +currentBuild.currentResult;
            
            emailext attachLog: true,
            mimeType: 'text/html',
            to: 'mimba.ngouana@advance-it-group.biz,if5.forge@advance-it-group.biz,daniel.kamga@advance-it-group.biz,yves.bakota@advance-it-group.biz,fanon.jupkwo@advance-it-group.biz',
            subject: last_started +"  :  "+ currentBuild.currentResult +"#$BUILD_NUMBER",
            body: "En pièce-jointe le fichier des logs de ${env.JOB_NAME}#$BUILD_NUMBER ";
        }
    }
}