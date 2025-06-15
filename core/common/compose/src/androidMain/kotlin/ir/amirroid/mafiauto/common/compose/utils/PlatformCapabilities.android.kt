package ir.amirroid.mafiauto.common.compose.utils

import android.annotation.SuppressLint
import android.os.Build

@SuppressLint("AnnotateVersionCheck")
actual object PlatformCapabilities {
    actual val blurSupported: Boolean
        get() = Build.VERSION.SDK_INT > Build.VERSION_CODES.S
}