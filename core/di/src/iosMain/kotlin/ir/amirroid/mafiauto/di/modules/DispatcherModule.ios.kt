package ir.amirroid.mafiauto.di.modules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual fun getIoDispatcher() = Dispatchers.IO