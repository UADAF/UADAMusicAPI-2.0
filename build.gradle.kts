import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.bundling.Jar

plugins {
    kotlin("jvm") version "1.3.20"
    `maven-publish`
}

group = "com.uadaf"
version = "2.5"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.google.code.gson:gson:2.8.5")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
val compileKotlin: KotlinCompile by tasks

publishing {
    repositories {
        maven {
            // change to point to your repo, e.g. http://my.org/repo
            url = uri("$buildDir/repo")
        }
    }
    publications {
        create("mavenJava", MavenPublication::class.java) {
            from(components["java"])
            artifact(sourcesJar)
        }
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(kotlin.sourceSets["main"].kotlin)
}