plugins {
    kotlin("jvm") version "2.4.21"
    id("dev.sayaya.gwt") version "2.3.0"
    signing
    id("maven-publish")
    id("com.vanniktech.maven.publish") version "0.37.0"
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
version = "2.5.0.9"

dependencies {
    implementation("org.jboss.elemento:elemento-core:2.5.14")
    implementation("org.projectlombok:lombok:1.18.48")
    annotationProcessor("org.projectlombok:lombok:1.18.48")
    testImplementation("dev.sayaya:gwt-test:2.3.0")
}
// labs-bundle 에서 npm 을 실행하는 Exec 공통 설정. Windows 는 cmd /c, 그 밖은 sh -c(셸의 PATH 로 npm 을 찾는다)
val labsBundleDir = file("src/main/webapp/labs-bundle")
val isWindows = System.getProperty("os.name").lowercase().contains("windows")
fun Exec.npm(vararg args: String) {
    workingDir = labsBundleDir
    if (isWindows) commandLine(listOf("cmd", "/c", "npm") + args)
    else commandLine("sh", "-c", (listOf("npm") + args).joinToString(" "))
}
tasks {
    // Labs bundle npm install 태스크
    val installLabsBundleDeps by registering(Exec::class) {
        group = "build"
        description = "Install labs-bundle npm dependencies"

        npm("install")

        // package.json 이 마지막 설치 때와 같으면 skip. node_modules 가 있어도 package.json 이 바뀌었으면 다시 설치한다
        // (전에는 node_modules 가 있기만 하면 건너뛰어 로컬에 오래된 @material/web·rollup 이 남았다)
        val packageJson = file("src/main/webapp/labs-bundle/package.json")
        val installedMarker = file("src/main/webapp/labs-bundle/node_modules/.sayaya-installed-package.json")
        onlyIf { !installedMarker.exists() || installedMarker.readText() != packageJson.readText() }
        doLast { installedMarker.writeText(packageJson.readText()) }
    }

    // Labs bundle 빌드 태스크
    val buildLabsBundle by registering(Exec::class) {
        group = "build"
        description = "Build Material Web Labs bundle"
        dependsOn(installLabsBundleDeps)

        npm("run", "build")
    }

    // 단일 classic-script 번들(@material/web all.js + labs, IIFE). Release 첨부물 sayaya-ui-<version>.js 의 원본
    val buildStandaloneBundle by registering(Exec::class) {
        group = "build"
        description = "Build standalone IIFE bundle (all.js + labs) into src/main/webapp/labs-bundle/dist/sayaya-ui.js"
        dependsOn(installLabsBundleDeps)
        environment("SAYAYA_UI_VERSION", project.version.toString())

        npm("run", "build:standalone")
    }

    // 단독 ESM 번들(md-tooltip, md-snackbar, md-toast 만 정의, 이미 정의돼 있으면 건너뜀). Release 첨부물 sayaya-ui-feedback-<version>.js 의 원본
    val buildFeedbackBundle by registering(Exec::class) {
        group = "build"
        description = "Build feedback ESM bundle (md-tooltip/md-snackbar/md-toast) into src/main/webapp/labs-bundle/dist/sayaya-ui-feedback.js"
        dependsOn(installLabsBundleDeps)
        environment("SAYAYA_UI_VERSION", project.version.toString())

        npm("run", "build:feedback")
    }

    // 단독 ESM 번들(md-top-app-bar, md-navigation-rail(-item), md-navigation-bar/tab 만 정의, 이미 정의돼 있으면 건너뜀). Release 첨부물 sayaya-ui-navigation-<version>.js 의 원본
    val buildNavigationBundle by registering(Exec::class) {
        group = "build"
        description = "Build navigation ESM bundle (top app bar / rail / navigation bar) into src/main/webapp/labs-bundle/dist/sayaya-ui-navigation.js"
        dependsOn(installLabsBundleDeps)
        environment("SAYAYA_UI_VERSION", project.version.toString())

        npm("run", "build:navigation")
    }

    // 테스트용 navigation ESM 번들 복사 태스크(navigation_esm.html 공존 검증)
    val copyNavigationBundleToTest by registering(Copy::class) {
        group = "build"
        description = "Copy sayaya-ui-navigation.js to test webapp"
        dependsOn(buildNavigationBundle)

        from("src/main/webapp/labs-bundle/dist/sayaya-ui-navigation.js")
        into("src/test/webapp")
    }

    // 테스트용 feedback ESM 번들 복사 태스크(feedback_esm.html 공존 검증)
    val copyFeedbackBundleToTest by registering(Copy::class) {
        group = "build"
        description = "Copy sayaya-ui-feedback.js to test webapp"
        dependsOn(buildFeedbackBundle)

        from("src/main/webapp/labs-bundle/dist/sayaya-ui-feedback.js")
        into("src/test/webapp")
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
        gwtVersion = "2.13.1"
        sourceLevel = "auto"
        modules = listOf("dev.sayaya.Ui")
        devMode {
            // 테스트 모듈 = src/test/java/dev/sayaya/*ElementTest.gwt.xml (컴포넌트를 추가해도 여기를 고칠 필요 없음)
            modules = fileTree("src/test/java/dev/sayaya") { include("*ElementTest.gwt.xml") }.files
                .map { "dev.sayaya." + it.name.removeSuffix(".gwt.xml") }.sorted()
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
        dependsOn(copyLabsBundleToTest, copyFeedbackBundleToTest, copyNavigationBundleToTest)
        // 테스트 페이지가 싣는 JS 번들을 입력으로 둔다. 없으면 JS 만 바꿨을 때 :test 가 UP-TO-DATE 로 건너뛰어진다
        inputs.files("src/test/webapp/labs.bundle.js", "src/test/webapp/sayaya-ui-feedback.js", "src/test/webapp/sayaya-ui-navigation.js")
            .withPropertyName("testBundles")
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