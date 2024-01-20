package ru.protei.barbolinsp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.protei.barbolinsp.di.ApplicationComponent
import ru.protei.barbolinsp.di.DaggerApplicationComponent

@HiltAndroidApp
class App : Application() {

    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this)
    }
}