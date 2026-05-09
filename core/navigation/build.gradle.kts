plugins {
    alias(libs.plugins.materialproductivity.android.library)
    alias(libs.plugins.materialproductivity.koin)
    alias(libs.plugins.compose)
}

android {
    namespace = "dev.stekl0.materialproductivity.core.navigation"
}

dependencies {
    api(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
    implementation(libs.kotlinx.collections.immutable)
}
