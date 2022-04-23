import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val ktor_auth_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val postgres_version: String by project

plugins {
    kotlin("jvm") version "1.6.10"
    application
    kotlin("plugin.serialization") version "1.5.20"
}

group = "me.administrator"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    google()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    maven {
        url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

dependencies {

    implementation("io.ktor:ktor-server-netty:1.6.3")
    implementation("io.ktor:ktor-html-builder:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
    //log
    implementation("ch.qos.logback:logback-classic:$logback_version")
    //cio server
    implementation("io.ktor:ktor-server-cio:$ktor_version")
    //content serialization
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    //database
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.postgresql:postgresql:$postgres_version")

    //test
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test")


}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("com.example.c_hostel.ApplicationKt")
}