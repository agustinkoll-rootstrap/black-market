import Dependencies.Versions.COMPOSE

plugins {
    with(Dependencies.Plugins) {
        id(ANDROID_APP)
        kotlin(ANDROID)
        kotlin(KAPT)
        id(GOOGLE_SERVICES)
        id(CRASHLYTICS)
        id (SONAR) version Dependencies.Versions.SONAR
    }
}

android {
    val projectKeyAlias: String by project
    val projectKeyPassword: String by project
    val projectStoreFile: String by project
    val projectStorePassword: String by project

    with(Dependencies.ConfigData) {
        compileSdk = COMPILE_SDK_VERSION
        buildToolsVersion = BUILD_TOOLS_VERSION

        defaultConfig {
            applicationId = "com.rootstrap.android"
            minSdk = MIN_SDK_VERSION
            targetSdk = TARGET_SDK_VERSION
            versionCode = 43
            versionName = VERSION_NAME
            testInstrumentationRunner = "com.rootstrap.android.CustomTestRunner"
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = projectKeyAlias
            keyPassword = projectKeyPassword
            storeFile = file(projectStoreFile)
            storePassword = projectStorePassword
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xallow-result-return-type")
    }

    kapt {
        correctErrorTypes = true
    }

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            animationsDisabled = true
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            isMinifyEnabled = false
            buildConfigField("String", "API_URL", "\"https://rails5-api-base.herokuapp.com/api/v1/\"")
            buildConfigField("String", "SECURE_KEY_ALIAS", "\"$projectKeyAlias\"")
            buildConfigField("String", "SECURE_FILE_NAME", "\"appPreferencesDev\"")
        }

        create("staging") {
            applicationIdSuffix = ".staging"
            versionNameSuffix = ".STAGING"
            isMinifyEnabled = false
            buildConfigField("String", "API_URL", "\"https://proj-staging.herokuapp.com/api/\"")
            buildConfigField("String", "SECURE_KEY_ALIAS", "\"$projectKeyAlias\"")
            buildConfigField("String", "SECURE_FILE_NAME", "\"appPreferencesStaging\"")
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("String", "API_URL", "\"https://proj-production.herokuapp.com/api/\"")
            buildConfigField("String", "SECURE_KEY_ALIAS", "\"$projectKeyAlias\"")
            buildConfigField("String", "SECURE_FILE_NAME", "\"appPreferences\"")
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
val ktlint: Configuration by configurations.creating

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":usecases"))
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_ui_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_ui_version"]}")
    implementation("androidx.compose.material:material:1.1.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_ui_version"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_ui_version"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_ui_version"]}")

    with(Dependencies.Android) {
        implementation(APP_COMPAT)
        implementation(CORE)
        implementation(CONSTRAINT_LAYOUT)
        implementation(LEGACY_SUPPORT_V4)
        implementation(LIFECYCLE_COMMON)
        implementation(LIFECYCLE_RUNTIME)
        implementation(LIFECYCLE_LIVEDATA)
        implementation(LIFECYCLE_EXTENSIONS)
        implementation(LIFECYCLE_VIEW_MODEL)
        implementation(PREFERENCE)
        implementation(ROOM_RUNTIME)
        implementation(NAVIGATION_FRAGMENT)
        implementation(NAVIGATION_UI)
        implementation(RECYCLERVIEW)
        implementation(WORK_RUNTIME)
    }

    with(Dependencies.JetpackCompose){
        implementation(compose_compiler)
        implementation(compose_ui)
        implementation(compose_tooling)
        implementation(compose_tooling_preview)
        implementation(compose_foundation)
        implementation(compose_foundation_layout)
        implementation(compose_material)
        implementation(compose_runtime)
        implementation(compose_navigation)
        implementation(compose_icons)
        implementation(compose_coil)
    }

    with(Dependencies.Common) {
        implementation(GLIDE)
        implementation(MIX_PANEL)
        implementation(OTTO)
        implementation(SECURITY_CRYPTO)
    }

    implementation(platform(Dependencies.Google.FIREBASE_BOM))

    with(Dependencies.Google) {
        implementation(MATERIAL)
        implementation(FIREBASE_CORE)
        implementation(FIREBASE_ANALYTICS)
        implementation(FIREBASE_CRASHLYTICS)
        implementation(GSON)
    }

    with(Dependencies.Koin) {
        implementation(ANDROID)
        implementation(ANDROID_COMPAT)
        implementation(ANDROID_NAVIGATION)
        implementation(COMPOSE)
        implementation(CORE)
        implementation(WORK_MANAGER)
    }

    with(Dependencies.Kotlin) {
        implementation(COROUTINES_ANDROID)
        implementation(KOTLIN_STDLIB)
        implementation(REFLECT)
    }

    with(Dependencies.Network) {
        implementation(CONVERTER_MOSHI)
        implementation(LOGGING_INTERCEPTOR)
        implementation(MOSHI)
        implementation(OKHTTP)
        implementation(RETROFIT)
        implementation(RETROFIT_GSON_CONVERTER)
    }

    with(Dependencies.Kapt) {
        kapt(GLIDE_KAPT)
        kapt(LIFECYCLE_KAPT)
        kapt(ROOM_KAPT)
    }

    ktlint(Dependencies.KT_LINT) {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }

    with(Dependencies.Test) {
        testImplementation(CORE_TESTING)
        testImplementation(COROUTINES_TEST)
        testImplementation(JUNIT)
        testImplementation(MOCKITO_CORE)
        testImplementation(MOCKK)
    }

    with(Dependencies.AndroidTest) {
        androidTestImplementation(ESPRESSO_CORE)
        androidTestImplementation(ESPRESSO_INTENTS)
        androidTestImplementation(EXT_JUNIT)
        androidTestImplementation(KOIN_TEST)
        androidTestImplementation(MOCK_WEBSERVER)
        androidTestImplementation(MOCKK_ANDROID)
        androidTestImplementation(RULES)
        androidTestImplementation(TEST_RUNNER)
        androidTestImplementation(UI_AUTOMATOR)
    }
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)
    group = "verification"

    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("src/**/*.kt")
    // See https://github.com/pinterest/ktlint#usage for more
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)
    group = "formatting"

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/**/*.kt")
}

tasks.getByPath("check").dependsOn(ktlint)

