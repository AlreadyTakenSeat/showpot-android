plugins {
    alias(libs.plugins.showpot.library)
}

android {
    namespace = "com.alreadyoccupiedseat.model"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    } }

dependencies {
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.annotation)
}