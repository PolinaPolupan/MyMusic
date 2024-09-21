plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dependency.analysis)
}

android {
    namespace = "com.example.testing"
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

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.hilt.android)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.hilt.common)
    implementation(libs.hilt.testing)

    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
}