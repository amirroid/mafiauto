package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.model.player.PlayerWithRole
import ir.amirroid.mafiauto.domain.repository.GameRepository

class HandleDefenseVoteResultUseCase(private val gameRepository: GameRepository) {
    operator fun invoke(voteMap: Map<PlayerWithRole, Int>) =
        gameRepository.handleDefenseVoteResult(voteMap)
}