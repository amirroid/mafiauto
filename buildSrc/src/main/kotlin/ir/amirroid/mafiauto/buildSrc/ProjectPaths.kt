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

    // Features
    const val intro = ":feature:intro"
    const val lobby = ":feature:lobby"
    const val assignRoles = ":feature:assign_roles"

    // Engine
    const val engine = ":game:engine"
}