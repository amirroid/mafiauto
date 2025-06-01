plugins {
    `kotlin-dsl`
}

dependencies {
    // Android & Compose plugins
    implementation(libs.plugin.android.compiler)
    implementation(libs.plugin.kotlin.compiler)
    implementation(libs.plugin.compose.compiler)
}

val namespace = "ir.amirroid.mafiauto.convention"

fun NamedDomainObjectContainer<PluginDeclaration>.createPlugin(
    name: String,
    implementationClass: String,
    subNamespace: String = ""
) = create(name) {
    val cleanedSubNamespace = subNamespace.trim('.')
    val fullNamespace = listOfNotNull(namespace, cleanedSubNamespace.takeIf { it.isNotEmpty() })
        .joinToString(".")

    this.implementationClass = "$fullNamespace.$implementationClass"
    this.id = "$namespace.$name"
}

gradlePlugin {
    plugins {
        createPlugin(
            name = "build-config",
            implementationClass = "DefaultSecretsPlugin",
            subNamespace = "config"
        )
        createPlugin(
            name = "multiplatform-plugin",
            implementationClass = "ComposeMultiplatformPlugin",
            subNamespace = "compose"
        )
        createPlugin(
            name = "kotlin-plugin",
            implementationClass = "KotlinMultiplatformPlugin",
            subNamespace = "kotlin"
        )
        createPlugin(
            name = "android-application-plugin",
            implementationClass = "AndroidApplicationPlugin",
            subNamespace = "android.application"
        )
        createPlugin(
            name = "android-library-plugin",
            implementationClass = "AndroidLibraryPlugin",
            subNamespace = "android.library"
        )
        createPlugin(
            name = "koin-plugin",
            implementationClass = "KoinPlugin",
            subNamespace = "koin"
        )
    }
}