package com.test.mercadolibretest.di

import com.test.mercadolibretest.util.MyAppExecutors
import org.koin.dsl.module

/**
 * Repository DI module.
 * Provides Repo dependency.
 */
val ExecutorDependencyTest = module {

    single {
        MyAppExecutors.instance
    }

}