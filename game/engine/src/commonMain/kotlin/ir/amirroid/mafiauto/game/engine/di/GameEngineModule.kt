package ir.amirroid.mafiauto.game.engine.di

import ir.amirroid.mafiauto.game.engine.GameEngine
import org.koin.dsl.module

val gameEngineModule = module {
    single {
        GameEngine(
            lastCardsProvider = get()
        )
    }
}