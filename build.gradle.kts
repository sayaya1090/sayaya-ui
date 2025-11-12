import org.docstr.gwt.GwtPluginExtension

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
    gwtDevMode {
        val extension = project.extensions.getByType(GwtPluginExtension::class.java)
        val sourceSets = project.extensions.getByType(SourceSetContainer::class.java)
        val mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
        val testSourceSet = sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME)

        extension.devMode.extraSourceDirs.from(
            mainSourceSet.allSource.sourceDirectories,
            mainSourceSet.resources.sourceDirectories,
            mainSourceSet.output,

            testSourceSet.allSource.sourceDirectories,
            testSourceSet.resources.sourceDirectories,
            testSourceSet.output,
            testSourceSet.runtimeClasspath
        )
        this.configureClasspath(project)
    }
    gwt {
        gwtVersion = "2.12.2"
        sourceLevel = "auto"
        modules = listOf("dev.sayaya.Ui")
        devMode {
            modules = listOf(
                "dev.sayaya.ButtonElementTest",
                "dev.sayaya.DialogElementTest",
                "dev.sayaya.CheckboxElementTest",
                "dev.sayaya.ChipElementTest",
                "dev.sayaya.DividerElementTest",
                "dev.sayaya.FocusRingElementTest",
                "dev.sayaya.IconElementTest",
                "dev.sayaya.RadioElementTest",
                "dev.sayaya.RippleElementTest",
                "dev.sayaya.SelectElementTest",
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
        exclude("**/*.class")
        from(sourceSets.main.get().allSource)
    }
}