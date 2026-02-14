plugins {
    kotlin("jvm") version "2.3.10"
    id("dev.sayaya.gwt") version "2.2.9"
    signing
    id("maven-publish")
    id("com.vanniktech.maven.publish") version "0.36.0"
}
repositories {
    mavenCentral()
    mavenLocal()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/sayaya1090/maven")
        credentials {
            username = project.findProperty("github_username") as String? ?: System.getenv("GITHUB_USERNAME")
            password = project.findProperty("github_password") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}
group = "dev.sayaya"
version = "2.4.1.3"

dependencies {
    implementation("org.jboss.elemento:elemento-core:2.4.8")
    implementation("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    testImplementation("dev.sayaya:gwt-test:2.2.9")
}
tasks {
    // Labs bundle npm install 태스크
    val installLabsBundleDeps by registering(Exec::class) {
        group = "build"
        description = "Install labs-bundle npm dependencies"
        workingDir = file("src/main/webapp/labs-bundle")

        if (System.getProperty("os.name").lowercase().contains("windows")) {
            commandLine("cmd", "/c", "npm", "install")
        } else {
            commandLine("sh", "-c", "npm install")
        }

        // node_modules가 이미 있으면 skip
        onlyIf {
            !file("src/main/webapp/labs-bundle/node_modules").exists()
        }
    }

    // Labs bundle 빌드 태스크
    val buildLabsBundle by registering(Exec::class) {
        group = "build"
        description = "Build Material Web Labs bundle"
        dependsOn(installLabsBundleDeps)
        workingDir = file("src/main/webapp/labs-bundle")

        if (System.getProperty("os.name").lowercase().contains("windows")) {
            commandLine("cmd", "/c", "npm", "run", "build")
        } else {
            commandLine("sh", "-c", "npm run build")
        }
    }

    // 테스트용 labs bundle 복사 태스크
    val copyLabsBundleToTest by registering(Copy::class) {
        group = "build"
        description = "Copy labs.bundle.js to test webapp"
        dependsOn(buildLabsBundle)

        from("src/main/webapp/labs.bundle.js")
        into("src/test/webapp")
    }

    gwt {
        minHeapSize = "1024M"
        maxHeapSize = "2048M"
        gwtVersion = "2.13.0"
        sourceLevel = "auto"
        modules = listOf("dev.sayaya.Ui")
        devMode {
            modules = listOf(
                "dev.sayaya.BadgeElementTest",
                "dev.sayaya.ButtonElementTest",
                "dev.sayaya.DialogElementTest",
                "dev.sayaya.CardElementTest",
                "dev.sayaya.CheckboxElementTest",
                "dev.sayaya.ChipElementTest",
                "dev.sayaya.DividerElementTest",
                "dev.sayaya.FocusRingElementTest",
                "dev.sayaya.IconElementTest",
                "dev.sayaya.ListElementTest",
                "dev.sayaya.MenuElementTest",
                "dev.sayaya.ProgressElementTest",
                "dev.sayaya.RadioElementTest",
                "dev.sayaya.RippleElementTest",
                "dev.sayaya.SelectElementTest",
                "dev.sayaya.SliderElementTest",
                "dev.sayaya.SwitchElementTest",
                "dev.sayaya.TabsElementTest",
                "dev.sayaya.TextFieldElementTest",
            )
            war = file("src/test/webapp")
        }
        generateJsInteropExports = true
        compiler {
            strict = true
            draftCompile = true
        }
    }
    test {
        useJUnitPlatform()
    }
    jar {
        from(sourceSets.main.get().allSource)
        dependsOn(buildLabsBundle)
    }

    // 빌드 태스크에도 labs bundle 빌드 추가
    gwtCompile {
        dependsOn(buildLabsBundle)
    }
    gwtGenerateTestHtml {
        dependsOn(copyLabsBundleToTest)
    }
    afterEvaluate {
        named<Jar>("sourcesJar") {
            dependsOn(compileJava)
        }
    }
}
signing {
    val signingKey = project.findProperty("signing.secretKey") as String? ?: System.getenv("GPG_PRIVATE_KEY")
    val signingPassword = project.findProperty("signing.passphrase") as String? ?: System.getenv("GPG_PASSWORD")
    useInMemoryPgpKeys(signingKey, signingPassword)
}
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/sayaya1090/maven")
            credentials {
                username = project.findProperty("github_username") as String? ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("github_password") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates(group.toString(), "ui", version.toString())

    pom {
        name.set("sayaya-ui")
        description.set("Material Design Web Components wrapper for GWT")
        url.set("https://github.com/sayaya1090/sayaya-ui")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("sayaya1090")
                name.set("sayaya")
                email.set("sayaya1090@gmail.com")
            }
        }

        scm {
            connection.set("scm:git:git://github.com/sayaya1090/sayaya-ui.git")
            developerConnection.set("scm:git:ssh://github.com/sayaya1090/sayaya-ui.git")
            url.set("https://github.com/sayaya1090/sayaya-ui")
        }
    }
}