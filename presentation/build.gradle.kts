plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.sina.presentation"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":domain")))

    implementation(Deps.core)
    implementation(Deps.appcompat)
    implementation(Deps.material)
    implementation(Deps.constraintLayout)
    implementation(Deps.legacy)

    implementation(LifeCycle.lifeCycleViewModel)
    implementation(LifeCycle.lifeCycleViewLivedata)

    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUi)
    implementation(Preferences.datastorePreferences)
    implementation(RestApi.retrofit)
    implementation(RestApi.gsonConverter)
    implementation(RestApi.okHttpLoggingInterceptor)
    implementation(RestApi.scalarConverter)
    implementation(DI.hilt)

    kapt(DI.hiltCompiler)
    kapt(DI.hiltAndroidCompiler)

    implementation(Log.timber)
    implementation(Sliders.carouselrecyclerview)
    implementation(Animations.lottie)
    implementation(WorkManager.workManager)
    implementation(Map.map)
    implementation(Map.location)
    implementation("com.karumi:dexter:6.2.2")



}