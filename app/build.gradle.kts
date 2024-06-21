plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val compose_version = "1.4.0"
val nav_version = "2.7.7"
val mui3_version = "1.1.2"
val hilt_version = "2.44"
val coroutines_version = "1.7.3"
val mockitoKotlinVersion = "5.1.0"
val play_service_auth_version = "20.7.0"
val kotlin_immutable_collection_version = "0.3.7"
val core_version = "1.13.1"

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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.compose.ui:ui:$compose_version")
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)


    implementation("androidx.compose.material3:material3:$mui3_version")

    implementation("io.coil-kt:coil-compose:2.5.0")

    //Firebase dependency
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")

    // Kotlin immutable collections
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlin_immutable_collection_version")

    //Google play service
    implementation("com.google.android.gms:play-services-auth:$play_service_auth_version")

    //kotlin reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.20")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.compose.foundation:foundation:1.7.0-beta01")

    // hilt dependency
    implementation("com.google.dagger:hilt-android:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    // Navigation in compose dependency
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("androidx.activity:activity-compose:1.8.1")

    // Constraint layout dependency
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    implementation("androidx.palette:palette-ktx:1.0.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("androidx.core:core-ktx:$core_version")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.0")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Hilt testing dependency
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hilt_version")
    // Make Hilt generate code in the androidTest folder
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hilt_version")

    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    androidTestImplementation(composeBom)

}

kapt {
}