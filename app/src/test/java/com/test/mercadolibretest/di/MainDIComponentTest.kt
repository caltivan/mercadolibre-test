package com.test.mercadolibretest.di

/**
 * Main Koin DI component.
 * Helps to configure
 * 1) Mockwebserver
 * 2) Usecase
 * 3) Repository
 */
fun configureTestAppComponent(baseApi: String) = listOf(
    ExecutorDependencyTest,
    MockWebServerDIPTest,
    configureNetworkModuleForTest(baseApi),
    RepoDependency
)

