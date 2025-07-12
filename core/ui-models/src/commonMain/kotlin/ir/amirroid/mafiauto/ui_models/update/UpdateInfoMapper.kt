package ir.amirroid.mafiauto.ui_models.update

import ir.amirroid.mafiauto.domain.model.settings.UpdateInfo

fun UpdateInfo.toUiModel() = UpdateInfoUiModel(
    needToUpdate = needToUpdate,
    release = UpdateInfoUiModel.ReleaseInfo(
        name = release.name,
        body = release.body,
        createdAt = release.createdAt,
        tag = release.tag,
        url = release.url
    )
)