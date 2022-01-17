plugins {
    id("java-library")
}

group = "up.visulog"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":analyzer"))
    api(project(":webgen"))
    api(project(":chartbuilder"))
    api(project(":tablebuilder"))
    api(project(":csvbuilder"))

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

}

tasks {

    jar {
        exclude("META-INF/*.RSA", "META-INF/*.SF","META-INF/*.DSA")

        dependsOn(":webgen:jar", ":chartbuilder:jar", ":pluginmanager:jar", ":tablebuilder:jar", ":csvbuilder:jar")
        duplicatesStrategy=DuplicatesStrategy.EXCLUDE
        from(configurations.compileClasspath.get().map { if (it.isDirectory()) it else zipTree(it) })
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
