package ir.amirroid.mafiauto.common.app.extensions

import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> Flow<List<T>>.mapToImmutableList() = map { it.toImmutableList() }