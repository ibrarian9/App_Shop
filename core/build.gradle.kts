plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

apply(from = "../shared_dependencies.gradle")

android {
    namespace = "com.app.core"
    compileSdk = 34

    lint {
        baseline = file("lint-baseline.xml")
    }

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    val retrofitVer = "2.9.0"
    val loggingVer = "4.12.0"
    val roomVer = "2.6.1"
    val sqlChiperVer = "4.4.0"
    val sqlVer = "2.4.0"

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVer")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVer")
    implementation("com.squareup.okhttp3:logging-interceptor:$loggingVer")

    // Room
    implementation("androidx.room:room-runtime:$roomVer")
    ksp("androidx.room:room-compiler:$roomVer")
    androidTestImplementation("androidx.room:room-testing:$roomVer")

    // Sqlchiper
    implementation("net.zetetic:android-database-sqlcipher:$sqlChiperVer")
    implementation("androidx.sqlite:sqlite-ktx:$sqlVer")
}