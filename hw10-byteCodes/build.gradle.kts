import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation("org.ow2.asm:asm-commons")
    implementation ("ch.qos.logback:logback-classic")
}

tasks {
    create<ShadowJar>("homeworkDemoJar") {
        archiveBaseName.set("homeworkDemo")
        archiveVersion.set("")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.otus.homework.HomeworkDemo",
                    "Premain-Class" to "ru.otus.homework.Agent"))
        }
        from(sourceSets.main.get().output)
        configurations = listOf(project.configurations.runtimeClasspath.get())
    }

    build {
        dependsOn("homeworkDemoJar")
    }
}
