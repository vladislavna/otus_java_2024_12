rootProject.name = "otusJava"
include("hw01-gradle")
include("hw04-generics")
include("hw06-annotations")
include("hw10-byteCodes")
include("hw12-solid")
include("hw15-structuralPatterns:demo")
include("hw15-structuralPatterns:homework")
include("hw16-io:demo")
include("hw16-io:homework")
include("hw17-nio")

include("hw18-jdbc:demo")
include("hw18-jdbc:homework")

include("hw20-hibernate")

include("hw21-jpql:class-demo")
include("hw21-jpql:homework-template")

include("hw22-cache")

//include("hw23-redis:counter")
//include("hw23-redis:data-source")
//include("hw23-redis:data-transformer")
//include("hw23-redis:data-listener")
//
//include("hw24-webServer")
//
//include("hw25-di:class-demo")
//include("hw25-di:homework-template")
//
//include("hw26-springBootMvc")

pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val sonarlint: String by settings
    val spotless: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
    }
}