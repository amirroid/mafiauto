package ir.amirroid.mafiauto.compat

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

class AndroidIconChangerCompat(
    private val context: Context
) : IconChangerCompat {

    private val colors = listOf("blue", "red", "green")

    private fun toPascalCase(s: String) = s.replaceFirstChar { it.uppercaseChar() }

    override fun changeColor(colorName: String) {
        val pm = context.packageManager
        val packageName = context.packageName

        val enabledAlias = toPascalCase(colorName) + "Alias"
        val mainActivity = ComponentName(packageName, "$packageName.MainActivity")

        pm.setComponentEnabledSetting(
            mainActivity,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        colors.forEach { color ->
            val aliasName = toPascalCase(color) + "Alias"
            val component = ComponentName(packageName, "$packageName.$aliasName")
            val state = if (aliasName == enabledAlias)
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            else
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED

            pm.setComponentEnabledSetting(
                component,
                state,
                PackageManager.DONT_KILL_APP
            )
        }
    }
}