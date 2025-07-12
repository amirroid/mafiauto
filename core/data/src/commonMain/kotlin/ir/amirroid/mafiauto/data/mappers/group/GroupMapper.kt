package ir.amirroid.mafiauto.data.mappers.group

import ir.amirroid.mafiauto.database.models.group.GroupEntity
import ir.amirroid.mafiauto.domain.model.group.Group

fun GroupEntity.toDomain() = Group(
    id = id,
    name = name
)