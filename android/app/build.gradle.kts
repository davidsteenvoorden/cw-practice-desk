import java.util.Properties

plugins {
    id("com.android.application")
}

// Release signing key, kept out of git. Without keystore.properties the release build uses the debug key.
val keystoreProps = Properties().apply {
    val f = rootProject.file("keystore.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}

android {
    namespace = "net.cwdesk"
    compileSdk = 37

    defaultConfig {
        applicationId = "net.cwdesk"
        minSdk = 26
        targetSdk = 37
        versionCode = 2
        versionName = "1.1"
    }

    // The app is the same page the desktop build uses.
    sourceSets["main"].assets.directories.add("../../web")

    // Lint needs an extra download and has nothing to say about a one-file WebView app.
    lint { checkReleaseBuilds = false }

    signingConfigs {
        if (keystoreProps.containsKey("storeFile")) {
            create("release") {
                storeFile = file(keystoreProps.getProperty("storeFile"))
                storePassword = keystoreProps.getProperty("storePassword")
                keyAlias = keystoreProps.getProperty("keyAlias")
                keyPassword = keystoreProps.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.findByName("release") ?: signingConfigs.getByName("debug")
        }
    }
}
