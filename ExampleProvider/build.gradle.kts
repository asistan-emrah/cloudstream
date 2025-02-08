plugins {
    id("com.android.library") version "7.4.2"
    id("org.jetbrains.kotlin.android") version "1.8.0"
}

android {
    namespace = "com.example.lokal"
    compileSdkVersion(34) // DÜZELTİLMİŞ SATIR

    defaultConfig {
        minSdk = 21
        // buildToolsVersion KALDIRILDI
    }

    buildTypes {
        named("release") {
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
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    
    // Cloudstream eklenti bağımlılıkları (gerekirse ekleyin)
    // implementation("com.github.recloudstream:cloudstream:3.6.5")
}
// use an integer for version numbers
version = -1


cloudstream {
    // All of these properties are optional, you can safely remove them

    description = "Lorem ipsum"
    authors = listOf("Cloudburst")

    /**
    * Status int as the following:
    * 0: Down
    * 1: Ok
    * 2: Slow
    * 3: Beta only
    * */
    status = 1

    tvTypes = listOf("Movie")

    requiresResources = true
    language = "en"

    // random cc logo i found
    iconUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2f/Korduene_Logo.png"
}

android {
    buildFeatures {
        viewBinding = true
    }
}
