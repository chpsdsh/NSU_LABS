plugins {
    id("java")
    id("application") // Добавляем поддержку запуска JAR
    id("com.github.johnrengelman.shadow") version "8.1.1" // Создание fat JAR
    id("jacoco") // Подключаем JaCoCo для покрытия кода тестами
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

    testImplementation("org.mockito:mockito-core:5.6.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.6.0")
}

// Конфигурация задач тестирования
tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    finalizedBy(tasks.jacocoTestReport) // После тестов запускаем отчёт JaCoCo
}

// Конфигурация JaCoCo
tasks.jacocoTestReport {
    dependsOn(tasks.test) // Генерация отчёта после тестов
    reports {
        xml.required.set(true) // XML-отчёт (например, для CI/CD)
        csv.required.set(false) // Отключаем CSV
        html.required.set(true) // Включаем HTML-отчёт
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco")) // Путь к HTML-отчёту
    }
}

// Конфигурация JAR-файла
tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}

// Конфигурация shadowJar (fat JAR)
tasks.shadowJar {
    archiveBaseName.set("Task2")
    archiveClassifier.set("")
    archiveVersion.set("")
}

// Указываем главный класс приложения
application {
    mainClass.set("Main") // Укажи свой главный класс
}
