package ir.amirroid.mafiauot.preferences.source.base

import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow

open class StorePreferencesSourceImpl<T : Any>(
    private val store: KStore<T>
) : PreferencesSource<T> {
    override fun get(): Flow<T?> {
        return store.updates
    }

    override suspend fun set(value: T) {
        store.set(value)
    }
}