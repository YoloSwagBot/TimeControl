plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("com.google.dagger.hilt.android")

    id("com.google.devtools.ksp")
}

android {
    namespace = "com.appstr.timecontrol"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.appstr.timecontrol"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.appstr.timecontrol.ApplicationTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}
dependencies {
    // Kotlin core
    implementation("androidx.core:core-ktx:1.12.0")
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    // Lifecycle-ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    // Compose
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    // Room
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")


    // Gson
    implementation("com.google.code.gson:gson:2.10.1")





    // test coroutines

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")


    // Hilt/Dagger
    implementation("com.google.dagger:hilt-android:2.48.1")
    ksp("com.google.dagger:hilt-android-compiler:2.48.1")
    testImplementation("com.google.dagger:hilt-android-testing:2.48.1")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48.1") // TestRunner
    kspAndroidTest("com.google.dagger:hilt-android-compiler:2.48.1")

    // JUnit
    testImplementation("junit:junit:4.13.2")


    // AndroidX test libs
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")

    // Truth(assertion library)
    testImplementation("com.google.truth:truth:1.1.4")
    androidTestImplementation("com.google.truth:truth:1.1.4")

    // Mockk
    testImplementation("io.mockk:mockk:1.12.4")

    // Mockito
    testImplementation("org.mockito:mockito-core:5.7.0")
    androidTestImplementation("org.mockito:mockito-android:5.7.0")


    // Testing-Compose
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
//    debugImplementation("androidx.compose.ui:ui-tooling")
}

