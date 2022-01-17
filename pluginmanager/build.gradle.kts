plugins {
    id("java-library")
}

group = "up.visulog"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":git"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    jar {
        dependsOn(":git:jar")
        exclude("META-INF/*.RSA", "META-INF/*.SF","META-INF/*.DSA")

        duplicatesStrategy=DuplicatesStrategy.EXCLUDE
        from(configurations.compileClasspath.get().map { if (it.isDirectory()) it else zipTree(it) })
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
