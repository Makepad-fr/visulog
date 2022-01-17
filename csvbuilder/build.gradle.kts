plugins {
    java
}

group = "up.visulog"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":analyzer"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
