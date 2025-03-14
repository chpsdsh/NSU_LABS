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
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("run") {
    mainClass.set("minesweeper.main.Main") // Указываем основной класс для запуска
    classpath = sourceSets["main"].runtimeClasspath // Указываем classpath для запуска
}
