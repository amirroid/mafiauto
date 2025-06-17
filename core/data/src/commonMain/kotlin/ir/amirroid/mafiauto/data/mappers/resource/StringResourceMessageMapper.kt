package ir.amirroid.mafiauto.data.mappers.resource

import ir.amirroid.mafiauto.domain.model.StringResourcesDescriptor
import ir.amirroid.mafiauto.game.engine.models.StringResourcesMessage

fun StringResourcesMessage.toDomain() = StringResourcesDescriptor(
    resource = resource,
    formatArgs = formatArgs
)