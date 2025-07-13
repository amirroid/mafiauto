package ir.amirroid.mafiauto.compat

import co.touchlab.kermit.Logger
import platform.UIKit.UIApplication
import platform.UIKit.setAlternateIconName
import platform.UIKit.supportsAlternateIcons

class IosIconChangerCompat : IconChangerCompat {
    override fun changeColor(colorName: String) {
        val app = UIApplication.sharedApplication
        if (!app.supportsAlternateIcons) return

        app.setAlternateIconName(colorName, completionHandler = { error ->
            if (error != null) {
                Logger.e("Failed to change icon: ${error.localizedDescription}")
            } else {
                Logger.d("Icon changed to $colorName")
            }
        })
    }
}