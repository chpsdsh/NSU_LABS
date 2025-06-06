plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")


    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("run") {
    standardInput = System.`in`
    mainClass.set("carfactory.main.Main")
    classpath = sourceSets["main"].runtimeClasspath
}