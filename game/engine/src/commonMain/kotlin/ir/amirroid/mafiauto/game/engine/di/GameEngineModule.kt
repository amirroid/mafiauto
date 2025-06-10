package ir.amirroid.mafiauto.game.engine.di

import ir.amirroid.mafiauto.game.engine.state.GameState
import org.koin.dsl.module

val gameEngineModule = module {
    factory { GameState() }
}