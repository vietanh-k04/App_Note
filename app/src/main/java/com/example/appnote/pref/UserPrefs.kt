package com.example.appnote.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


class UserPrefs(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun setDarkMode(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_DARK_MODE, enabled) }
    }

    fun isDarkMode() : Boolean {
        return prefs.getBoolean(KEY_DARK_MODE, false)
    }

    companion object {
        const val KEY_DARK_MODE = "DARK_MODE"
    }
}