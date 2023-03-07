package com.example.testingrecyclerview.util

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    @JvmField val countingIdlResource = CountingIdlingResource(RESOURCE)

    fun increment()
    {
        countingIdlResource.increment()
    }

    fun decrement()
    {
        if (!countingIdlResource.isIdleNow)
        {
            countingIdlResource.decrement()
        }
    }
}