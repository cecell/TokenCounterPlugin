plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.0"
    kotlin("jvm") version "2.1.0"
}

group = "com.example"
version = "1.0"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
intellij {
    version.set("2025.1.3")
    type.set("PC")
    plugins.set(listOf("PythonCore"))
    updateSinceUntilBuild.set(false)  // Added to prevent version checking issues
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
}

kotlin {
    jvmToolchain(17)

    compilerOptions {
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_1)
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_1)
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("241.0")
        untilBuild.set("251.*")
    }

    // Disable buildSearchableOptions
    buildSearchableOptions {
        enabled = false
    }

    // Optional: Configure proper test running
    test {
        useJUnitPlatform()
    }
    // Make sure resources are processed
    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE  // Add this line
        from("src/main/resources") {
            include("**/*")
        }
    }
    buildPlugin {
        archiveBaseName.set("token-counter")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}