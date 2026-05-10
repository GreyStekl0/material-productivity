plugins {
    alias(libs.plugins.materialproductivity.android.library)
    alias(libs.plugins.materialproductivity.android.room)
    alias(libs.plugins.materialproductivity.koin)
}

android {
    namespace = "dev.stekl0.materialproductivity.core.database"
}

dependencies {
    api(projects.core.model)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}
