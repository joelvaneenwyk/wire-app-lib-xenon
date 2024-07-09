/*
 * This file was generated by the Gradle 'init' task.
 */

import com.google.protobuf.gradle.id
import org.gradle.kotlin.dsl.`java-library`

plugins {
    `java-library`
    `maven-publish`
    id("com.google.protobuf") version("0.9.4")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    mavenLocal()
    google()
}

dependencies {
    implementation("com.wire", "cryptobox4j", "1.3.0")

    implementation("com.fasterxml.jackson.core", "jackson-annotations", "2.15.1")
    implementation("com.fasterxml.jackson.core", "jackson-databind", "2.15.2")

    implementation("javax.validation", "validation-api", "2.0.1.Final")

    implementation("com.google.protobuf", "protobuf-java", "3.24.3")

    implementation("org.jdbi", "jdbi3-sqlobject", "3.37.1")

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.9.2")
    testImplementation("org.junit.jupiter", "junit-jupiter-engine", "5.9.2")

    testImplementation("org.postgresql", "postgresql", "42.5.4")

    testImplementation("org.flywaydb", "flyway-core", "9.15.1")

    testImplementation("org.slf4j", "slf4j-simple", "2.0.6")
}

protobuf {
//    plugins {
//        id("grpc") {
//            artifact = libs.grpc.genJava.get().toString()
//        }
//    }
//
    protoc {
        // TODO(Benoit) Replace with `artifact = libs.protobuf.protoc.get().toString()` once gRPC-java
        //  starts supporting protoc 4+. See https://github.com/grpc/grpc-java/issues/10976
        artifact = "com.google.protobuf:protoc:3.25.3"
    }
//
//    generateProtoTasks {
//        ofSourceSet("test").forEach {
//            it.plugins {
//                // Apply the "grpc" plugin whose spec is defined above, without
//                // options.  Note the braces cannot be omitted, otherwise the
//                // plugin will not be added. This is because of the implicit way
//                // NamedDomainObjectContainer binds the methods.
//                id("grpc") {}
//            }
//        }
//    }
}

buildscript {
    dependencies {
        classpath(libs.protobuf.gradlePlugin)
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

group = "com.wire"
version = "1.5.5"
description = "Xenon"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xmaxerrs", "1000", "-Xmaxwarns", "1000"))
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

