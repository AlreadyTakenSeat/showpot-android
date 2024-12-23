import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    alias(libs.plugins.showpot.application)
    alias(libs.plugins.showpot.firebase)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.showpot.hilt)
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.alreadyoccupiedseat.showpot"

    defaultConfig {
        applicationId = "com.alreadyoccupiedseat.showpot"
        targetSdk = 34
        versionCode = 2
        versionName = "0.9.0"

        // for using it on the .kt files
        buildConfigField(
            "String",
            "kakaoNativeAppKeyString",
            properties.getProperty("kakao_sdk_native_key_string")
        )
        // for using it on the manifest
        manifestPlaceholders["kakaoNativeAppKey"] = properties["kakao_sdk_native_key"] as Any

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    applicationVariants.configureEach {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val today = dateFormat.format(Date())
        val appName = "showPot"
        val versionName = defaultConfig.versionName.orEmpty()
        this.outputs.configureEach {
            val flavorName = if (flavorName.isNullOrBlank()) "" else flavorName
            buildOutputs.forEach { _ ->
                val buildType =buildType.name
                val archivesBaseName = if (flavorName.isNotEmpty()) {
                    "${appName}-${flavorName}-${buildType}-v$versionName-$today.apk"
                } else {
                    "${appName}-${buildType}-v$versionName-$today.apk"
                }
                val variantOutputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
                variantOutputImpl.outputFileName =  archivesBaseName
            }
        }
    }

}


dependencies {

    implementation(project(":feature:home"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:notification"))
    implementation(project(":feature:mypage"))
    implementation(project(":feature:login"))
    implementation(project(":feature:search"))
    implementation(project(":feature:subscription-genre"))
    implementation(project(":feature:subscription-artist"))
    implementation(project(":feature:subscribed-artist"))
    implementation(project(":feature:show-detail"))
    implementation(project(":feature:myalarm-setting"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:myfinished-show"))
    implementation(project(":feature:myfavorite-show"))
    implementation(project(":feature:entire-show"))
    implementation(project(":feature:withdraw"))
    implementation(project(":feature:account"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":core:notification"))
    implementation(project(":core:datastore"))
    implementation(project(":core:data"))

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.coil.kt.compose)

    implementation(libs.kakao)

    implementation(libs.firebase.messaging)

    // rememberPermissionState
    implementation(libs.accompanist.permissions)

}