plugins {
    id("java-library")
    id("org.openjfx.javafxplugin") version "0.0.8"

}

group = "up.visulog"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":analyzer"))
    implementation(project(":pluginmanager"))
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.0.0.202111291000-r")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}