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
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
}

tasks.test {
    useJUnitPlatform()
}


tasks.register<JavaExec>("run") {
    standardInput = System.`in`
    mainClass.set("minesweeper.main.Main")
    classpath = sourceSets["main"].runtimeClasspath
}
