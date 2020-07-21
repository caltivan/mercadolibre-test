package com.test.mercadolibretest

import androidx.multidex.MultiDexApplication
import com.test.mercadolibretest.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initiateKoin()

    }

    private fun initiateKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(provideDependency())
        }
    }

    open fun provideDependency() = appComponent
}