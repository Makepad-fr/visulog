
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
    implementation(project(":pluginmanager"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    jar {
        archiveFileName.set("visulog.jar")
        from(configurations.compileClasspath.get().map { if (it.isDirectory()) it else zipTree(it) })

        manifest {
            attributes["Main-Class"]= application.mainClass.get()
        }
    }
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

// TODO: Add task compileJava with compiler arguments

