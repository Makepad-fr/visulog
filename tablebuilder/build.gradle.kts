plugins {
    id("java-library")
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
tasks {
    jar {
        exclude("META-INF/*.RSA", "META-INF/*.SF","META-INF/*.DSA")

        dependsOn(":analyzer:jar")
        duplicatesStrategy=DuplicatesStrategy.EXCLUDE
        from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    }
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
