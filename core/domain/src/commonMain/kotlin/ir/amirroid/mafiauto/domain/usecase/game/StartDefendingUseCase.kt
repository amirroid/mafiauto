package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.repository.GameRepository

class StartDefendingUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(players: List<PlayerWithRole>) = gameRepository.startDefending(players)
}