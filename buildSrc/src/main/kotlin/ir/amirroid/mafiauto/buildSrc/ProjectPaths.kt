package ir.amirroid.mafiauto.buildSrc

@Suppress("ConstPropertyName")
object ProjectPaths {
    // Core
    const val designSystem = ":core:design-system"
    const val navigation = ":core:navigation"
    const val di = ":core:di"
    const val resources = ":core:resources"
    const val data = ":core:data"
    const val database = ":core:database"
    const val domain = ":core:domain"
    const val uiModels = ":core:ui-models"
    const val preferences = ":core:preferences"

    // Features
    const val intro = ":feature:intro"
    const val groups = ":feature:groups"
    const val lobby = ":feature:lobby"
    const val assignRoles = ":feature:assign-roles"
    const val finalDebate = ":feature:final-debate"
    const val reveal = ":feature:reveal"
    const val settings = ":feature:settings"
    const val room = ":feature:room"
    const val night = ":feature:night"
    const val libraries = ":feature:libraries"

    // Engine
    const val engine = ":game:engine"
}