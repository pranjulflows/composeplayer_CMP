
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinxSerialization)
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"

    id("com.google.devtools.ksp") version "1.9.23-1.0.20"
    id("de.jensklingenberg.ktorfit") version "2.0.0-beta1"
}

detekt {
    // Version of detekt that will be used. When unspecified the latest detekt
    // version found will be used. Override to stay on the same version.
    toolVersion = "1.23.6"

    // The directories where detekt looks for source files.
    // Defaults to `files("src/main/java", "src/test/java", "src/main/kotlin", "src/test/kotlin")`.
    source.setFrom("src/main/java", "src/main/kotlin")

    // Builds the AST in parallel. Rules are always executed in parallel.
    // Can lead to speedups in larger projects. `false` by default.
    parallel = false

    // Define the detekt configuration(s) you want to use.
    // Defaults to the default detekt configuration.
    config.setFrom("config.yml")

    // Applies the config files on top of detekt's default config file. `false` by default.
    buildUponDefaultConfig = false

    // Turns on all the rules. `false` by default.
    allRules = false

    // Specifying a baseline file. All findings stored in this file in subsequent runs of detekt.
    baseline = file("baseline.xml")

    // Disables all default detekt rulesets and will only run detekt with custom rules
    // defined in plugins passed in with `detektPlugins` configuration. `false` by default.
    disableDefaultRuleSets = false

    // Adds debug output during task execution. `false` by default.
    debug = false

    // If set to `true` the build does not fail when the
    // maxIssues count was reached. Defaults to `false`.
    ignoreFailures = false

    // Android: Don't create tasks for the specified build types (e.g. "release")
    ignoredBuildTypes = listOf("release")

    // Android: Don't create tasks for the specified build flavor (e.g. "production")
    ignoredFlavors = listOf("production")

    // Android: Don't create tasks for the specified build variants (e.g. "productionRelease")
    ignoredVariants = listOf("productionRelease")

    // Specify the base path for file paths in the formatted reports.
    // If not set, all file paths reported will be absolute file path.
    basePath = projectDir.absolutePath
}

kotlin {

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.media3.exoplayer)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(libs.kamel.image)
            implementation(libs.compose.multiplatform.media.player)
            implementation(libs.ktorfit.lib)
//            implementation(libs.navigation.compose)
//            implementation(libs.lifecycle.viewmodel.compose)

            implementation(compose.ui)
            implementation(compose.components.resources)

            implementation(compose.components.uiToolingPreview)
            implementation(libs.ktorfit.lib)
            implementation(libs.landscapist.coil3)

//            implementation("de.jensklingenberg.ktorfit:ktorfit-lib-light:$ktorfitVersion")
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktorfit.converters.response)
            implementation(libs.ktorfit.converters.call)
            implementation(libs.ktorfit.converters.flow)

            // compose multiplatform
            implementation(libs.mvvm.compose) // api mvvm-core, getViewModel for Compose Multiplatform
            implementation(libs.mvvm.flow.compose) // api mvvm-flow, binding extensions for Compose Multiplatform
            implementation(libs.mvvm.livedata.compose) // api mvvm-livedata, binding extensions for Compose Multiplatform

//            implementation(libs.voyager.navigator)
//            implementation(libs.voyager.screenModel)
//            implementation(libs.voyager.bottomSheetNavigator)
//            implementation(libs.voyager.transitions)
//            implementation(libs.voyager.tabNavigator)

//            implementation(libs.sandwich)
//            implementation(libs.sandwich.ktor)
//            implementation(libs.sandwich.ktorfit)
            // Only needed when you want to use Kotlin Serialization
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
        }
    }
}

android {
    namespace = "org.macamps.musicplayer"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.macamps.musicplayer"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        with("de.jensklingenberg.ktorfit:ktorfit-ksp:2.0.0-beta1") {
            add("kspCommonMainMetadata", this)
            add("kspAndroid", this)
            add("kspAndroidTest", this)
            add("kspIosX64", this)
            add("kspIosX64Test", this)
            add("kspIosArm64", this)
            add("kspIosArm64Test", this)
            add("kspIosSimulatorArm64", this)
            add("kspIosSimulatorArm64Test", this)
        }
    }
}
dependencies {
    implementation(libs.androidx.palette.ktx)
}
