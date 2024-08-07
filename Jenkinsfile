pipeline {
    agent any

    options {
        githubProjectProperty(projectUrlStr: "https://github.com/SirBlobman/US-SGP-Hook")
    }

    environment {
        DISCORD_URL = credentials('PUBLIC_DISCORD_WEBHOOK')
    }

    triggers {
        githubPush()
    }

    tools {
        jdk "JDK 8"
    }

    stages {
        stage("Gradle: Build") {
            steps {
                withGradle {
                    sh("./gradlew --refresh-dependencies --no-daemon clean build")
                }
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'build/libs/US-SGP-Hook-*.jar', fingerprint: true
        }

        always {
            script {
                discordSend webhookURL: DISCORD_URL, title: "UltimateStacker ShopGUIPlus Hook",
                        link: "${env.BUILD_URL}", result: currentBuild.currentResult,
                        description: """\
                            **Branch:** ${env.GIT_BRANCH}
                            **Build:** ${env.BUILD_NUMBER}
                            **Status:** ${currentBuild.currentResult}""".stripIndent(),
                        enableArtifactsList: false, showChangeset: true
            }
        }
    }
}
