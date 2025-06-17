package ir.amirroid.mafiauto.ui_models.resource

import ir.amirroid.mafiauto.domain.model.StringResourcesDescriptor

fun StringResourcesDescriptor.toUiModel() = MessageStringResourceUiModel(
    resource = resource,
    formatArgs = formatArgs
)