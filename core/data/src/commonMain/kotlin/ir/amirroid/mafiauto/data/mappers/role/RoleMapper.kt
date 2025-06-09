package ir.amirroid.mafiauto.data.mappers.role

import ir.amirroid.mafiauto.domain.model.RoleDescriptor
import ir.amirroid.mafiauto.game.engine.role.Role

fun Role.toDescriptor(): RoleDescriptor {
    return RoleDescriptor(
        name = name,
        key = key
    )
}