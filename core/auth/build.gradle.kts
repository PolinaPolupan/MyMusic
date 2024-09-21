plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.plugin.serialization)
    alias(libs.plugins.dependency.analysis)
}

android {
    namespace = "com.example.auth"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "com.example.mymusic.core.testing.MyMusicTestRunner"
        consumerProguardFiles("consumer-rules.pro")

        manifestPlaceholders["appAuthRedirectScheme"] = "com.example.mymusic"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.hilt.android)
    implementation(libs.appauth)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit.test.ext)
}