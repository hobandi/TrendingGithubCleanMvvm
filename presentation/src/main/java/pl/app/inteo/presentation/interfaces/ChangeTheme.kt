package pl.app.inteo.presentation.interfaces

import android.content.Context

interface ChangeTheme {

    fun isDarkTheme(context: Context): Boolean
    fun isLightTheme(context: Context): Boolean
    fun setNightMode(nightMode: Boolean)
}
