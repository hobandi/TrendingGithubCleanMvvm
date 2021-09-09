package pl.app.inteo

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.app.inteo.di.appModules
import pl.app.inteo.presentation.interfaces.ChangeTheme
import pl.app.inteo.presentation.utils.PresentationPreferencesHelper

class AppApplication : Application() {

    private val changeTheme: ChangeTheme by inject()

    private val preferencesHelper: PresentationPreferencesHelper by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            modules(appModules)
        }
        changeTheme.setNightMode(preferencesHelper.isNightMode)
    }
}
