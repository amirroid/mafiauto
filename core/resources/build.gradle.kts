plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.compose.multiplatform)
    alias(libs.plugins.aboutLibraries)
}

val aboutLibsOutput = file("src/commonMain/composeResources/files/aboutlibraries.json")

aboutLibraries {
    export {
        outputFile = aboutLibsOutput
    }
}

tasks.named("copyNonXmlValueResourcesForCommonMain") {
    if (!aboutLibsOutput.exists()) {
        dependsOn("exportLibraryDefinitions")
    }
}