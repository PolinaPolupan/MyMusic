plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dependency.analysis)
}

android {
    namespace = "com.example.mymusic.core.database"
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

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.paging.room)
    implementation(libs.hilt.android)
    implementation(libs.androidx.room.ktx)
    implementation(project(":core:model"))
    androidTestImplementation(project(":core:network"))

    ksp(libs.hilt.compiler)
    ksp(libs.androidx.room.compiler)
    ksp(libs.androidx.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(libs.androidx.junit.test.ext)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}