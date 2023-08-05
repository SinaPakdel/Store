plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.sina.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":domain")))

    implementation(Deps.core)
    implementation(DI.hilt)

    kapt(DI.hiltCompiler)
    kapt(DI.hiltAndroidCompiler)
    implementation(Log.timber)
    implementation(Preferences.datastorePreferences)

    implementation(RestApi.retrofit)
    implementation(RestApi.gsonConverter)
    implementation(RestApi.okHttpLoggingInterceptor)
    implementation(RestApi.scalarConverter)
    implementation(WorkManager.workManager)

}