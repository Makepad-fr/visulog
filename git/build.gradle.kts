plugins {
id("java-library")
}

group = "up.visulog"
version = "0.0.1"

repositories {
mavenCentral()
}

dependencies {
implementation("org.eclipse.jgit:org.eclipse.jgit:6.0.0.202111291000-r")

testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    jar {
        exclude("META-INF/*.RSA", "META-INF/*.SF","META-INF/*.DSA")

        duplicatesStrategy=DuplicatesStrategy.EXCLUDE
        from(configurations.compileClasspath.get().map { if (it.isDirectory()) it else zipTree(it) })
    }
}

tasks {
    jar {
        duplicatesStrategy=DuplicatesStrategy.EXCLUDE
        from(configurations.compileClasspath.get().map { if (it.isDirectory()) it else zipTree(it) })
    }
}

tasks.getByName<Test>("test") {
useJUnitPlatform()


}


