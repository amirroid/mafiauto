package ir.amirroid.mafiauto.ui_models.update

import ir.amirroid.mafiauto.domain.model.UpdateInfo

fun UpdateInfo.toUiModel() = UpdateInfoUiModel(
    needToUpdate = needToUpdate,
    info = UpdateInfoUiModel.ReleaseInfo(
        name = info.name,
        body = info.body,
        createdAt = info.createdAt,
        tag = info.tag,
    )
)