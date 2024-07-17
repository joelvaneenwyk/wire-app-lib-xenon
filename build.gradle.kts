/*
 * wire-app-lib-xenon | Build settings
 */

import com.google.protobuf.gradle.id

val projectVersion = "1.5.6-SNAPSHOT"
val projectGroupId = "com.wire"

version = projectVersion
group = projectGroupId

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

plugins {
    kotlin("jvm")
    `java-library`
    `maven-publish`
    signing
    id("com.google.protobuf") version ("0.9.4")
    id("org.sonatype.gradle.plugins.scan") version ("2.8.2")
}

dependencies {
    implementation(libs.cryptobox4j)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.databind)

    implementation(libs.javax.validation)

    implementation(libs.jdbi3.sqlobject)

    implementation(libs.protobuf.gradle)
    implementation(libs.protobuf.java)
    implementation(libs.protobuf.java.util)
    implementation(libs.protobuf.protoc)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)

    testImplementation(libs.postgresql)
    testImplementation(libs.slf4j.simple)
    testImplementation(libs.flyway.gradle)

    testRuntimeOnly(libs.junit.platform.launcher)

    implementation(kotlin("stdlib-jdk8"))
}

protobuf {
    plugins {
        id("grpc") {
            artifact = libs.grpc.genJava.get().toString()
        }
    }

    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }

    generateProtoTasks {
        ofSourceSet("test").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without
                // options.  Note the braces cannot be omitted, otherwise the
                // plugin will not be added. This is because of the implicit way
                // NamedDomainObjectContainer binds the methods.
                id("grpc") {}
            }
        }
    }
}

nexusIQScan {
    // Make sure to use an user with the role "Application Evaluator" in the given IQ Server application
    username = System.getenv("NEXUS_USERNAME") ?: "admin"
    password = System.getenv("NEXUS_PASSWWORD") ?: "pass"
    serverUrl = "http://localhost:8070"
    applicationId = "app"
}

publishing {
    publications {
        all {
            version = projectVersion
            group = projectGroupId
        }

        create("xenon", MavenPublication::class) {
            from(components["java"])
            pom {
                name.set("wire-app-lib-xenon")
                description.set("Xenon")
            }
        }
    }

    repositories {
        val allowSonaType = (System.getenv("ALLOW_SONATYPE") ?: "false") == "true"
        val org = System.getenv("GITHUB_ACTOR") ?: project.findProperty("gpr.org") as String? ?: "joelvaneenwyk"
        val token = System.getenv("GITHUB_TOKEN") ?: project.findProperty("gpr.key") as String?

        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/joelvaneenwyk/wire-app-lib-xenon")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: org
                password = System.getenv("GITHUB_TOKEN") ?: token
            }
        }

        if (allowSonaType) {
            maven {
                name = "OSSRH"
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = System.getenv("MAVEN_USERNAME") ?: org
                    password = System.getenv("MAVEN_PASSWORD") ?: token
                }
            }
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(11)
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(
        listOf(
            "-Xlint:-options", "-Xlint:deprecation", "-Xmaxerrs", "1000", "-Xmaxwarns", "1000"
        )
    )
}

tasks.named<Test>("test") {
    sourceSets {
        named("test") {
            resources {
                srcDir("src/test/resources")
            }
        }
    }
    maxParallelForks = 2
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        showExceptions = true
        events("passed")
    }
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
    if (JavaVersion.current().isJava8Compatible) {
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
    }
}
