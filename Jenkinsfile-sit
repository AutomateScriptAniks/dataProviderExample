pipeline {
  agent any

  stages {
    stage('Run Tests') {
      steps {
        sh './gradlew test'
      }
      post {
        always {
          archiveArtifacts artifacts: 'build/reports/tests/test/**', fingerprint: true
        }
      }
    }
    stage('Cleanup') {
      steps {
        deleteDir()
      }
    }
  }
}
