package ru.androidlab.todoproj

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.androidlab.todoproj.di.appModule

class ToDoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ToDoApp)
            modules(listOf(appModule))
        }
    }
}
