plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("./key/keystore.jks")
            storePassword = "@ComuniCAA123"
            keyAlias = "@ComuniCAA123"
            keyPassword = "@ComuniCAA123"
        }
    }

    namespace = "com.example.comunicaa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.comunicaa"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }

        release {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("prod") {
            dimension = "version"
            resValue("string", "app_name", "ComuniCAA")

            buildConfigField("String", "NAME", "\"ComuniCAA\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions { jvmTarget = "1.8" }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    // Android Default Dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.dynamic.features.fragment)
    androidTestImplementation(libs.androidx.navigation.testing)

    // Preferences Data Store
    implementation(libs.androidx.datastore.preferences)

    // Live Data
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    kapt(libs.androidx.lifecycle.compiler)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.lifecycle.process)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.room.compiler)

    // Gson
    implementation(libs.gson)

    // Firebase Auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Firebase Realtime Database
    implementation(libs.firebase.database)

    // Firebase Storage
    implementation(libs.firebase.storage)

    // Firebase Crashlytics
    implementation(libs.firebase.crashlytics)

    // Firebase Analytics
    implementation(libs.firebase.analytics)

    // Picasso
    implementation(platform(libs.okhttp.bom))
    implementation(libs.picasso)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Lottie Files
    implementation(libs.lottie)
}

kapt { correctErrorTypes = true }