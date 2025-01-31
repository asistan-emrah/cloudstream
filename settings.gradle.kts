// settings.gradle.kts
pluginManagement {
    repositories {
        google()  // Android Gradle Plugin için
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "CloudstreamPlugins"

// Projeye dahil edilen modüller (tüm eklenti modüllerinizi buraya ekleyin)
include(
    "ExampleProvider",
    // Diğer modüllerinizi buraya ekleyin:
    // "AnotherProvider",
    // "MovieProvider"
)
