package com.test.mercadolibretest.di

import com.test.mercadolibretest.respository.ItemRepository
import org.koin.dsl.module

/**
 * Repository DI module.
 * Provides Repo dependency.
 */
val RepoDependency = module {

    factory {
        ItemRepository()
    }

}