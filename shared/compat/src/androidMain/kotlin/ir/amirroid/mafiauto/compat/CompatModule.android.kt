package ir.amirroid.mafiauto.compat

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

actual val compatModule = module {
    single { AndroidIconChangerCompat(androidContext()) }.bind<IconChangerCompat>()
}