import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("com.android.library")
    id("com.google.devtools.ksp")
}

val kuiklyVersion: String by project

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    js(IR) {
        browser()
    }
    sourceSets {
        commonMain.dependencies {
            implementation("com.tencent.kuikly-open:core-annotations:$kuiklyVersion")
            implementation(project(":skeleton"))
            implementation(project(":skeleton-compose"))
        }
    }
}

dependencies {
    add("kspAndroid", "com.tencent.kuikly-open:core-ksp-jvm:$kuiklyVersion")
}

android {
    namespace = "com.kuikly.skeleton.demo"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }
}
