import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import com.android.build.gradle.BaseExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2") // AGP sürümünü düşürün
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0") // Sürümü düzeltin
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

fun Project.cloudstream(configuration: CloudstreamExtension.() -> Unit) = extensions.getByName<CloudstreamExtension>("cloudstream").configuration()
fun Project.android(configuration: BaseExtension.() -> Unit) = extensions.getByName<BaseExtension>("android").configuration()

android {
    namespace = "com.keyiflerolsun"
    compileSdkVersion(34) // compileSdk = 34 → BU SATIRI DÜZELTİN

    defaultConfig {
        minSdk = 21
        targetSdk = 34 // targetSdk = 34 ekleyin
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
            freeCompilerArgs.addAll(
                listOf(
                    "-Xno-call-assertions",
                    "-Xno-param-assertions",
                    "-Xno-receiver-assertions"
                )
            )
        }
    }
}

cloudstream {
    setRepo(System.getenv("GITHUB_REPOSITORY") ?: "https://github.com/asistan-emrah/cloudstream")
    authors = listOf("cloudstream")
}

dependencies {
    val cloudstream by configurations
    val implementation by configurations

    cloudstream("com.lagradost:cloudstream3:pre-release")
    implementation(kotlin("stdlib"))                                             
    implementation("com.github.Blatzar:NiceHttp:0.4.11")                         
    implementation("org.jsoup:jsoup:1.18.3")                                     
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")   
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")          
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")      
}

task<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
