package ir.amirroid.mafiauto.common.compose.base

import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateMap

fun <K : Any, V : Any> snapshotStateMapSaver() =
    listSaver<SnapshotStateMap<K, V>, Pair<K, V>>(
        save = { stateMap -> stateMap.toList() },
        restore = { it.toMutableStateMap() }
    )