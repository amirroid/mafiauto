package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.repository.GameRepository

class GetDefenseCandidatesUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(playerVotes: Map<PlayerWithRole, Int>) =
        gameRepository.getDefenseCandidates(playerVotes)
}