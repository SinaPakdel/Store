object Versions {
    const val core = "1.10.1"
    const val appcompat = "1.6.1"
    const val material = "1.8.0"
    const val constrainLayout = "2.1.4"
    const val legacy = "1.0.0"

    const val jUnit = "4.13.2"
    const val testJUnit = "1.1.5"
    const val espresso = "3.5.1"

    const val lifeCycleViewModel = "2.6.1"
    const val lifeCycleViewLivedata = "2.6.1"

    const val navigationFragment = "2.5.3"
    const val navigationUi = "2.5.3"

    const val viewModel = "1.7.2"

    const val retrofit = "2.9.0"
    const val gsonConverter = "2.9.0"
    const val okHttpLoggingInterceptor = "4.10.0"
    const val scalarConverter = "2.1.0"
    const val jsoup = "1.13.1"

    const val hilt = "2.46.1"
    const val hiltCompiler = "2.46.1"

    const val room = "2.5.1"
    const val roomCompiler = "2.5.1"
    const val roomKtx = "2.5.1"

    const val datastorePreferences = "1.0.0"

    const val glide = "4.15.1"

    const val coil = "2.2.2"

    const val lottie = "3.4.2"

    const val swipeRefreshLayout = "1.1.0"

    const val carouselrecyclerview = "1.2.6"

    const val shimmer = "0.5.0"
    const val shimmerRecyclerview = "0.4.1"

    const val timber = "5.0.1"

    const val workManager = "2.8.1"

    const val mapService = "18.1.0"
    const val locationService = "21.0.1"


}

object Deps {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constrainLayout}"
    const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacy}"

}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"
    const val testJUnit = "androidx.test.ext:junit:${Versions.testJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object LifeCycle {
    const val lifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleViewModel}"
    const val lifeCycleViewLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycleViewLivedata}"
}

object ViewModel {
    const val viewModelDelegation = "androidx.activity:activity-ktx:${Versions.viewModel}"
}

object ORM {
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object DI {
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hiltCompiler}"
}

object RestApi {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
    const val scalarConverter = "com.squareup.retrofit2:converter-scalars:${Versions.gsonConverter}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
    const val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"
}

object Navigation {
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragment}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationUi}"
}

object ImageLoaders {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object Preferences {
    const val datastorePreferences = "androidx.datastore:datastore-preferences:${Versions.datastorePreferences}"
}

object Animations {
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    const val shimmerRecyclerview = "com.todkars:shimmer-recyclerview:${Versions.shimmerRecyclerview}"
}

object ProgressBar {
    const val circularBar = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
}

object Sliders {
    const val carouselrecyclerview = "com.github.sparrow007:carouselrecyclerview:${Versions.carouselrecyclerview}"
}

object Log {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object WorkManager {
    const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"
}

object Map{
    const val map="com.google.android.gms:play-services-maps:${Versions.mapService}"
    const val location="com.google.android.gms:play-services-location:${Versions.locationService}"
}