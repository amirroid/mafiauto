package ir.amirroid.mafiauto.resources

import mafiauto.core.resources.generated.resources.Res

object Files {
    suspend fun aboutLibraries() = Res.readBytes("files/aboutlibraries.json")
}