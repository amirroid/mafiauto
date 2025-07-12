package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.model.player.PlayerWithRole
import ir.amirroid.mafiauto.domain.repository.GameRepository

class ApplyPlayerEffectUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(
        effectName: String,
        player: PlayerWithRole,
        targetPlayers: List<PlayerWithRole>
    ) = gameRepository.applyPlayerEffect(effectName, player, targetPlayers)
}