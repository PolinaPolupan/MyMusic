plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.plugin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.mymusic.core.network"
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
    implementation(libs.retrofit)
    implementation(libs.hilt.android)
    implementation(libs.network.response.adapter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.room.ktx)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.coroutines.test.jvm)
    implementation(project(":core:auth"))
    implementation(project(":core:database"))
    implementation(project(":core:common"))
    //    implementation(project(":core:testing"))
    implementation(libs.androidx.core.ktx)
    implementation("androidx.compose.runtime:runtime:${libs.versions.androidx.compose}")

    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit.test.ext)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}