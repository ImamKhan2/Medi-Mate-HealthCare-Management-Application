buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.google.services)
        //classpath (com.android.tools.build:gradle:8.0.2) // Or your current version
        //classpath (com.google.gms:google-services:4.3.15) // Required for Firebase
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}