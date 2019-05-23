object Versions {
    const val kotlin = "1.3.31"
    const val appCompat = "1.0.2"
    const val constraint = "1.1.3"
    const val androidX = "1.0.0"
    const val groupie = "2.3.0"
    const val gms = "4.2.0"
    const val gradle = "3.4.1"
    const val firebaseCore = "16.0.9"
    const val firebaseDatabase = "16.1.0"
    const val coroutine = "1.1.1"
    const val lifecycle = "2.0.0"
    const val picasso = "2.71828"
}

object Dependencies {
    const val kotlinLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val ktx = "androidx.core:core-ktx:${Versions.appCompat}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.androidX}"
    const val cardView = "androidx.cardview:cardview:${Versions.androidX}"
    const val material = "com.google.android.material:material:${Versions.androidX}"

    const val gms = "com.google.gms:google-services:${Versions.gms}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"

    const val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
    const val firebaseDataBase = "com.google.firebase:firebase-database:${Versions.firebaseDatabase}"

    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
    const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"

    const val viewModel = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val groupie = "com.xwray:groupie:${Versions.groupie}"
    const val groupieKotlin = "com.xwray:groupie-kotlin-android-extensions:${Versions.groupie}"
    const val aacLifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object VersionsTest {

    const val junit = "4.12"
    const val runner = "1.1.1"
    const val espresso = "3.1.1"
}

object DependenciesTest {

    const val junit4 = "junit:junit:${VersionsTest.junit}"
    const val androidRunner = "androidx.test:runner:${VersionsTest.runner}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${VersionsTest.espresso}"
}


