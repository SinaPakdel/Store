plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.sina.core"
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

    implementation(Deps.core)
    implementation(Deps.appcompat)
    implementation(Deps.material)
    implementation(Deps.legacy)

    implementation(DI.hilt)
    kapt(DI.hiltCompiler)
    kapt(DI.hiltAndroidCompiler)

    implementation(ORM.room)
    implementation(ORM.roomKtx)
    kapt(ORM.roomCompiler)

    implementation(LifeCycle.lifeCycleViewModel)
    implementation(LifeCycle.lifeCycleViewLivedata)

    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUi)

    implementation(RestApi.retrofit)
    implementation(RestApi.gsonConverter)
    implementation(RestApi.okHttpLoggingInterceptor)
    implementation(RestApi.scalarConverter)

    implementation(Preferences.datastorePreferences)
    implementation(Log.timber)
    implementation(Animations.lottie)
    implementation(Log.timber)
    implementation(ImageLoaders.glide)
    implementation(ImageLoaders.coil)
    implementation(WorkManager.workManager)

}