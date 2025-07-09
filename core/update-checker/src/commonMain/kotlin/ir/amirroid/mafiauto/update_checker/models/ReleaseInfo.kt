package ir.amirroid.mafiauto.update_checker.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ReleaseInfo(
    val id: Long,
    val name: String,
    val body: String,
    @SerialName("tag_name")
    val tag: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("html_url")
    val url: String
)