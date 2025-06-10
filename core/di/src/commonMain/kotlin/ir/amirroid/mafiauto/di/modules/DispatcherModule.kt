package ir.amirroid.mafiauto.di.modules

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val IoDispatcher = qualifier("io")
val MainDispatcher = qualifier("main")

val dispatcherModule = module {
    factory<CoroutineDispatcher>(IoDispatcher) { Dispatchers.IO }
    factory<CoroutineDispatcher>(MainDispatcher) { Dispatchers.Main }

    factory { get<CoroutineDispatcher>(IoDispatcher) }
}