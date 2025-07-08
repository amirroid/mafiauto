package ir.amirroid.mafiauto.common.app.utils

import kotlinx.collections.immutable.toImmutableList

fun <T> emptyImmutableList() = emptyList<T>().toImmutableList()