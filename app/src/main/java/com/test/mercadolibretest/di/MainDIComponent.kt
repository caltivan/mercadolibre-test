package com.test.mercadolibretest.di

/**
 * Main dependency component.
 * This will create and provide required dependencies with sub dependencies.
 */
val appComponent = listOf(ExecutorDependency,RepoDependency,networkDependency)