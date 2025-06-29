package ir.amirroid.mafiauot.preferences.utils

import kotlinx.io.files.Path

internal fun String.toPath() = Path(this)