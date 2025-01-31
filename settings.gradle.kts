// settings.gradle.kts
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")  // JitPack eklendi
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
