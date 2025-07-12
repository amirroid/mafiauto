package ir.amirroid.mafiauto.data.mappers.release

import ir.amirroid.mafiauto.domain.model.settings.UpdateInfo
import ir.amirroid.mafiauto.update_checker.models.ReleaseInfo

fun ReleaseInfo.toDomain() = UpdateInfo.ReleaseInfo(
    name = name,
    body = body,
    createdAt = createdAt,
    tag = tag,
    url = url
)