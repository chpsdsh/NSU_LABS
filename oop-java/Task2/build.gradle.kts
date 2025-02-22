plugins {
    id("java")
    id("application") // Добавляем поддержку запуска JAR
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

application {
    mainClass.set("Main") // Укажи свой главный класс
}


tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}
