group = "kr.kro.minestar"
version = "1.0.0"

val plugins = File("C:\\Users\\MineStar\\Desktop\\MC Server folder\\MCserver 1.18.1 - SacrificerOfSlayer\\plugins")

tasks {
    compileKotlin{
        kotlinOptions.jvmTarget = "17"
    }

    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }

    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")
        archiveVersion.set(project.version.toString())
        archivePath.delete()
        doLast {
            // jar file copy
            copy {
                from(archiveFile)
                into(plugins)
            }
        }
    }
}