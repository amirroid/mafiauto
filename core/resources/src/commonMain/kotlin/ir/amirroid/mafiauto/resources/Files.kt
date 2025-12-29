package ir.amirroid.mafiauto.resources

object Files {
    suspend fun aboutLibraries() = Res.readBytes("files/aboutlibraries.json")
}