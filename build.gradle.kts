
plugins {
    kotlin("jvm") version "1.9.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    implementation("io.ktor:ktor-server-html-builder:2.3.0")
    implementation("io.ktor:ktor-server-auth:2.3.0")
    implementation("com.zaxxer:HikariCP:5.0.0") // For database connection
    implementation("org.postgresql:postgresql:42.6.0") // PostgreSQL driver

    // Testing dependencies
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("com.example.MainKt")
}
