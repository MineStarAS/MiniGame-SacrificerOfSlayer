plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "kr.kro.minestar"
version = "1.0.0"

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
            name = "sonatype-oss-snapshots"
        }
        maven(url = "https://jitpack.io/")
    }

    dependencies {
        implementation(kotlin("stdlib"))
        compileOnly("net.kyori:adventure-api:4.10.1")
        compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")

        //MineStar
//        implementation("com.github.MineStarAS:Utility-API:1.0.0")
        implementation(files("C:\\Users\\MineStar\\Desktop\\MC Server folder\\libs\\Utility-API-1.0.1.jar"))

        //other
        // https://mvnrepository.com/artifact/commons-io/commons-io
        implementation("commons-io:commons-io:2.11.0")
    }
}
