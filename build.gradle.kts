import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
    kotlin("plugin.serialization") version "1.5.10"
}

group = "me.administrator"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-netty:1.6.3")
    implementation("io.ktor:ktor-html-builder:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
    //log
    implementation("ch.qos.logback:logback-classic:1.2.10")
    //cio server
    implementation("io.ktor:ktor-server-cio:1.6.2")
    //content serialization
    implementation("io.ktor:ktor-serialization:1.6.7")

    //database
    implementation("org.jetbrains.exposed:exposed:0.17.14")
    implementation("org.postgresql:postgresql:42.2.24")

    //test
    testImplementation("io.ktor:ktor-server-test-host:1.6.7")
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