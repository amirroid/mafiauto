package ir.amirroid.mafiauto.compat

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val compatModule = module {
    singleOf(::IosIconChangerCompat).bind<IconChangerCompat>()
}