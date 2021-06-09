package app.rodrigonovoa.dogsrandomfacts.common

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    private val PREFS = "app.rodrigonovoa.dogsrandomfacts.prefs"
    private val SHARED_USERNAME = "SHARED_USERNAME"
    private val SHARED_SKIP_SPLASH = "SHARED_SKIP_SPLASH"
    private val SHARED_FIRST_OPENING = "SHARED_FIRST_OPENING"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS, 0)

    var name: String
        get() = prefs.getString(SHARED_USERNAME, "")!!
        set(value) = prefs.edit().putString(SHARED_USERNAME, value).apply()

    var skip_splash: Boolean
        get() = prefs.getBoolean(SHARED_SKIP_SPLASH, false)
        set(value) = prefs.edit().putBoolean(SHARED_SKIP_SPLASH, value).apply()

    var first_opening: Boolean
        get() = prefs.getBoolean(SHARED_FIRST_OPENING, true)
        set(value) = prefs.edit().putBoolean(SHARED_FIRST_OPENING, value).apply()
}