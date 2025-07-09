package ir.amirroid.mafiauto.domain.model

data class UpdateInfo(
    val needToUpdate: Boolean,
    val release: ReleaseInfo
) {
    data class ReleaseInfo(
        val name: String,
        val body: String,
        val createdAt: String,
        val tag: String,
        val url: String
    )
}