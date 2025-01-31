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
    "FilmekseniProvider"
    // Diğer modüllerinizi buraya ekleyin:
    // "AnotherProvider",
    // "MovieProvider"
)
