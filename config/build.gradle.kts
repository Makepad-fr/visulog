plugins {
    id("java-library")
}

group = "up.visulog"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":pluginmanager"))
    implementation(project(":analyzer"))
    implementation(project(":git"))
    implementation(project(":webgen"))
    implementation(project(":chartbuilder"))
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.0.0.202111291000-r")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
