package com.test.mercadolibretest.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Helper class that handles and manage the different threads execution
 */
class MyAppExecutors
/**
 * Constructor
 *
 * @param networkThread     executor on charge of the access to the external storage
 * @param mainThread executor on charge to notify to the ui thread
 */ private constructor(
    val networkThread: Executor,
    val mainThread: Executor
) {

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler =
            Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        private val LOCK = Any()
        private const val THREAD_COUNT = 3
        private var sInstance: MyAppExecutors? = null
        val instance: MyAppExecutors?
            get() {
                if (sInstance == null) {
                    synchronized(
                        LOCK
                    ) {
                        sInstance = MyAppExecutors(
                            Executors.newFixedThreadPool(THREAD_COUNT),
                            MainThreadExecutor()
                        )
                    }
                }
                return sInstance
            }
    }

}