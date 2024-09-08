import com.example.buildsrc.Configs
import com.example.buildsrc.Dependencies
import com.example.buildsrc.TestDependencies
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
}

android {
    namespace = Configs.namespace
    compileSdk = Configs.compileSdkVersion

    val properties = Properties()
    file(Configs.configProperties).inputStream().use { properties.load(it) }
    defaultConfig {
        applicationId = Configs.applicationId
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion
        versionCode = Configs.versionCode
        versionName = Configs.versionName

        testInstrumentationRunner = Configs.instrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("boolean", "isFirebaseLocal", "false")
            buildConfigField(
                "String",
                "DATADOG_CLIENT_TOKEN",
                "\"${properties["DATADOG_CLIENT_TOKEN"]}\""
            )
            buildConfigField("String", "DATADOG_APP_ID", "\"${properties["DATADOG_APP_ID"]}\"")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("boolean", "isFirebaseLocal", "false")
            buildConfigField(
                "String",
                "DATADOG_CLIENT_TOKEN",
                "\"${properties["DATADOG_CLIENT_TOKEN"]}\""
            )
            buildConfigField("String", "DATADOG_APP_ID", "\"${properties["DATADOG_APP_ID"]}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Configs.kotlinCompilerExtensionVersion
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.composeAnimation)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeMaterialIconsExtended)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.coreSplashScreen)
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.lifecycleViewModelCompose)
    implementation(Dependencies.lifecycleLiveDataKtx)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    implementation(Dependencies.hiltAndroid)
    implementation(Dependencies.material)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.navigationFragmentKtx)
    implementation(Dependencies.navigationUiKtx)
    implementation(Dependencies.hiltNavigationCompose)
    ksp(Dependencies.hiltCompiler)

    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.firebaseAnalyticsKtx)
    implementation(Dependencies.firebaseUiAuth)
    implementation(Dependencies.firebaseFirestoreKtx)
    implementation(Dependencies.playServicesAuth)
    implementation(Dependencies.firebaseRemoteConfig)

    implementation(Dependencies.datastorePreferences)

    implementation(Dependencies.datadogRum)
    implementation(Dependencies.datadogLogs)

    testImplementation(Dependencies.junit)
    testImplementation(TestDependencies.coroutinesTest)
    testImplementation(TestDependencies.mockk)
    testImplementation(TestDependencies.truth)

    androidTestImplementation(Dependencies.testExtJunit)
    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(TestDependencies.composeUiTestJunit4)
    androidTestImplementation(Dependencies.hiltAndroidTesting)
    androidTestImplementation(Dependencies.hiltNavigationCompose)
    androidTestImplementation(TestDependencies.coroutinesTest)
    androidTestImplementation(TestDependencies.archCore)
    androidTestImplementation(TestDependencies.testRunner)
    androidTestImplementation(TestDependencies.testCore)
    kspAndroidTest(Dependencies.hiltCompiler)

    debugImplementation(Dependencies.composeUiTooling)
    debugImplementation(TestDependencies.composeUiTestManifest)
}