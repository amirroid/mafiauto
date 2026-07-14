package ir.amirroid.mafiauto.di.modules

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val IoDispatcher = qualifier("io")
val MainDispatcher = qualifier("main")

expect fun getIoDispatcher(): CoroutineDispatcher

val dispatcherModule = module {
    factory<CoroutineDispatcher>(IoDispatcher) { getIoDispatcher() }
    factory<CoroutineDispatcher>(MainDispatcher) { Dispatchers.Main }

    factory { get<CoroutineDispatcher>(IoDispatcher) }
}