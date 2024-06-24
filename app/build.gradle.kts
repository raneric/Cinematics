plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.sgg.cinematics"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sgg.cinematics"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.sgg.cinematics.CinematicsTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.profileinstaller)
    "baselineProfile"(project(":baselineprofile"))
    val composeBom = platform(libs.androidx.compose.boom)
    implementation(composeBom)


    implementation(libs.androidx.compose.mui3)

    implementation(libs.coil.kt)

    //Firebase dependency
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.storage)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    // Kotlin immutable collections
    implementation(libs.jetbrains.kotlinx.collections)

    //Google play service
    implementation(libs.google.android.gms)

    //kotlin reflection
    implementation(libs.jetbrains.kotlinx.reflection)

    // Android Studio Preview support
    implementation(libs.androidx.compose.preview)
    debugImplementation(libs.androidx.compose.tooling)

    implementation(libs.androidx.compose.fondation)

    // hilt dependency
    implementation(libs.google.dagger.hilt)
    implementation(libs.androidx.hilt.navigation)
    kapt(libs.google.dagger.hilt.compiler)

    // Navigation in compose dependency
    implementation(libs.androidx.compose.navigation)

    implementation(libs.androidx.activity)

    // Constraint layout dependency
    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.lifecycle.compose)

    implementation(libs.androidx.datastore)

    implementation(libs.andoirdx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.google.android.material)
    implementation(libs.androidx.compose.mui3.winsize)

    // UI Tests
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.junit4)
    testImplementation(libs.jetbrains.kotlinx.coroutins.test)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso)

    // Hilt testing dependency
    androidTestImplementation(libs.google.dagger.hilt.android)
    // Make Hilt generate code in the androidTest folder
    kaptAndroidTest(libs.google.dagger.hilt.android.compiler)

    androidTestImplementation(libs.androidx.compose.navigation.test)

    androidTestImplementation(composeBom)

}

kapt {
}