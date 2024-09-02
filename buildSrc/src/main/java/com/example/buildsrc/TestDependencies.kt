package com.example.buildsrc

object TestDependencies {
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val archCore = "androidx.arch.core:core-testing:${Versions.archCore}"
    const val composeUiTestJunit4 =
        "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}"
    const val composeUiTestManifest =
        "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}"
    const val testRunner = "androidx.test:runner:${Versions.testCore}"
    const val testCore = "androidx.test:core-ktx:${Versions.testCore}"
}