plugins {
    kotlin("jvm") version "2.2.21"
    id("dev.sayaya.gwt") version "2.2.7"
    id("maven-publish")
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
version = "2.4.1"

dependencies {
    implementation("org.jboss.elemento:elemento-core:2.3.2")
    implementation("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    testImplementation("dev.sayaya:gwt-test:2.2.7")
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
        gwtVersion = "2.12.2"
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
    publications {
        register("maven", MavenPublication::class) {
            groupId = project.group.toString()
            artifactId = "ui"
            version = project.version.toString()
            from(project.components["java"])
        }
    }
}