plugins {
    alias(libs.plugins.materialproductivity.android.library)
    alias(libs.plugins.materialproductivity.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.stekl0.materialproductivity.core.data"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    api(projects.core.model)
    api(libs.kotlinx.coroutines.core)

    implementation(projects.core.database)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlinx.coroutines.test)
}
