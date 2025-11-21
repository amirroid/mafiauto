package ir.amirroid.mafiauto.compat

import org.koin.dsl.module

actual val compatModule = module {
    factory<IconChangerCompat> {
        object : IconChangerCompat {
            override fun changeColor(colorName: String) {
                // no-op
            }
        }
    }
}