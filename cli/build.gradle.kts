
plugins {
    id("application")
}

group = "up.visulog"
version = "0.0.1"

application.mainClass.set("up.visulog.cli.VisulogCLI")

repositories {
    mavenCentral()
}

dependencies {
    implementation ("info.picocli:picocli:4.6.2")
    annotationProcessor("info.picocli:picocli-codegen:4.6.2")
    implementation(project(":chartbuilder"))
    implementation(project(":webgen"))
    implementation(project(":config"))
    implementation(project(":analyzer"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

// TODO: Add task compileJava with compiler arguments

