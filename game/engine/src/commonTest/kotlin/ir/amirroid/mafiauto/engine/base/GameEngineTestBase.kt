package ir.amirroid.mafiauto.engine.base

import ir.amirroid.mafiauto.engine.repository.FakePlayersRepository
import ir.amirroid.mafiauto.game.engine.GameEngine
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.provider.last_card.LastCardsProviderImpl
import ir.amirroid.mafiauto.game.engine.provider.roles.RolesProviderImpl
import kotlin.test.BeforeTest

open class GameEngineTestBase {
    protected val lastCardsProvider = LastCardsProviderImpl()
    protected val playerRepository = FakePlayersRepository(RolesProviderImpl())
    protected val engine = GameEngine(lastCardsProvider)
    protected val players: List<Player>
        get() = engine.players.value


    @BeforeTest
    fun setup() {
        engine.reset()
        engine.updatePlayers(playerRepository.getPlayers())
    }
}