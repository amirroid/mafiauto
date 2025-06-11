package ir.amirroid.mafiauto.reveal.mappers

import ir.amirroid.mafiauto.domain.model.Alignment
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.reveal.models.PlayerWithRoleUiModel

fun PlayerWithRole.toUiModel(): PlayerWithRoleUiModel {
    return PlayerWithRoleUiModel(
        playerId = player.id,
        playerName = player.name,
        roleName = role.name,
        roleKey = role.key,
        roleExplanation = role.explanation,
        roleAlignment = when (role.alignment) {
            Alignment.Mafia -> Resources.strings.mafia
            Alignment.Civilian -> Resources.strings.civilian
            Alignment.Neutral -> Resources.strings.neutral
        }
    )
}