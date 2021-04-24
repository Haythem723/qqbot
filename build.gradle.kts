plugins {
    val kotlinVersion = "1.4.30"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.6.2"
}

group = "org.qqbot"
version = "1.0"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.jdeferred.v2/jdeferred-core
    implementation("org.jdeferred.v2", "jdeferred-core", "2.0.0")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j", "slf4j-simple", "1.7.30")
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.6.0")
}

tasks.test {
    useJUnitPlatform()
}