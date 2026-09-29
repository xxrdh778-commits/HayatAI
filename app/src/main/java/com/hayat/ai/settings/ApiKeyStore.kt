package com.hayat.ai.settings

import android.content.Context

object ApiKeyStore {

    private const val PREFS_NAME = "hayat_ai_settings"
    private const val API_KEY = "api_key"

    fun save(context: Context, key: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(API_KEY, key)
            .apply()
    }

    fun get(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(API_KEY, "") ?: ""
    }
}
