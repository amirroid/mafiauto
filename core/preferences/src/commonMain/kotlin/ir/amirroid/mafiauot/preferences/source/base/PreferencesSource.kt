package ir.amirroid.mafiauot.preferences.source.base

import kotlinx.coroutines.flow.Flow

interface PreferencesSource<T : Any> {
    fun get(): Flow<T?>
    suspend fun set(value: T)
}