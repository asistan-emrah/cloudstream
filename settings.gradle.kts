rootProject.name = "CloudstreamPlugins"

// This file sets what projects are included. Every time you add a new project, you must add it
// to the includes below.
// settings.gradle.kts
pluginManagement {
    repositories {
        google()  // AGP için Google deposu
        mavenCentral()
        gradlePluginPortal()
    }
}

// Plugins are included like this
include(
    "ExampleProvider"
)
