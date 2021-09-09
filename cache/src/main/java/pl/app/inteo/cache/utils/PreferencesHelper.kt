package pl.app.inteo.cache.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper constructor(context: Context) {

    companion object {
        private const val PREF_BUFFER_PACKAGE_NAME = "org.buffer.android.boilerplate.preferences"

        private const val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_BUFFER_PACKAGE_NAME, Context.MODE_PRIVATE)

    /**
     * Store and retrieve the last time data was cached
     */
    var lastCacheTime: Long
        get() = preferences.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = preferences.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

}
