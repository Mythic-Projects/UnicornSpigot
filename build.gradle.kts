import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    group = "org.mythicprojects.unicornspigot"
    version = "0.0.1-1.8.8.STABLE"

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }

    repositories {
        mavenCentral()
        maven(url = "https://libraries.minecraft.net")
        maven(url = "https://repo.titanvale.net/releases")
        maven(url = "https://repo.titanvale.net/snapshots")
        maven(url = "https://storehouse.okaeri.eu/repository/maven-public")
    }
}

subprojects {
    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
    }
    tasks.withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name()
    }
    tasks.withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name()
    }

    tasks.withType<Test> {
        testLogging {
            showStackTraces = true
            exceptionFormat = TestExceptionFormat.FULL
            events(TestLogEvent.STANDARD_OUT)
        }
    }
}

ext["gitHash"] = project.getCurrentGitHash()

fun Project.getCurrentGitHash(): String {
    val result = exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
    }

    return result.toString().trim()
}
