package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.model.player.PlayerWithRole
import ir.amirroid.mafiauto.domain.repository.GameRepository

class HandleFinalTrustVotesUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(trustVotes: Map<PlayerWithRole, PlayerWithRole>) =
        gameRepository.handleFinalTrustVotes(trustVotes)
}