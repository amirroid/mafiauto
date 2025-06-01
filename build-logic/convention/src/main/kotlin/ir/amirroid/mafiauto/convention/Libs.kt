package ir.amirroid.mafiauto.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.ComposePlugin

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.androidLibs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("androidLibs")

internal val Project.composeDependencies: ComposePlugin.Dependencies
    get() = extensions.getByType<ComposeExtension>().dependencies