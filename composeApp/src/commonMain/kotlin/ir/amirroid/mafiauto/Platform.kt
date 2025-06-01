package ir.amirroid.mafiauto

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform