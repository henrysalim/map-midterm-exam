import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.midtermexam"
    compileSdk = 36
    val localProperties = Properties()
    val localPropertiesFile = File(rootDir, "local.properties")
    if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
        localPropertiesFile.inputStream().use {
            localProperties.load(it)
        }
    }

    val supabaseUrl: String =
        localProperties.getProperty("SUPABASE_URL") ?: error("SUPABASE_URL not found in local.properties")
    val postgresPassword: String =
        localProperties.getProperty("POSTGRES_PASSWORD") ?: error("POSTGRES_PASSWORD not found in local.properties")
    val supabaseAnonKey: String =
        localProperties.getProperty("SUPABASE_ANON_KEY") ?: error("SUPABASE_ANON_KEY not found in local.properties")

    buildFeatures {
        viewBinding = true
        buildConfig = true
        resValues = true
    }

    defaultConfig {
        applicationId = "com.example.midtermexam"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField("String", "SUPABASE_URL", "$supabaseUrl")
            buildConfigField("String", "POSTGRES_PASSWORD", "$postgresPassword")
            buildConfigField("String", "SUPABASE_ANON_KEY", "$supabaseAnonKey")
        }
        debug {
            buildConfigField("String", "SUPABASE_URL", "$supabaseUrl")
            buildConfigField("String", "POSTGRES_PASSWORD", "$postgresPassword")
            buildConfigField("String", "SUPABASE_ANON_KEY", "$supabaseAnonKey")

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    implementation("androidx.fragment:fragment-ktx:1.5.7")
    // Retrofit untuk koneksi ke API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Glide untuk memuat gambar dari URL
    implementation("com.github.bumptech.glide:glide:4.16.0")

// Komponen standar AndroidX
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

// Coroutines untuk proses background (misalnya mengambil data API)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

// Coroutines for asynchronous tasks
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")

// Image loading library (Coil is modern and Kotlin-first)
    implementation("io.coil-kt:coil:2.5.0")

//    for supabase connection
//    serializer
    implementation("io.github.jan-tennert.supabase:serializer-moshi:3.2.6")
    // Google Sign-In
//    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("io.github.jan-tennert.supabase:auth-kt:3.2.6")

    // Supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:3.2.6"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt:1.4.7")
    implementation("io.github.jan-tennert.supabase:gotrue-kt:1.4.7")
    implementation("io.github.jan-tennert.supabase:realtime-kt:1.4.7")

    // Ktor for HTTP requests
    implementation("io.ktor:ktor-client-android:2.3.7")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
//    implementation("io.ktor:ktor-client-[engine]:3.0.0-rc-1")
}