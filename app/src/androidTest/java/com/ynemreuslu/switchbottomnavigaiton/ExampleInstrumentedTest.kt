package com.ynemreuslu.switchbottomnavigaiton

import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ynemreuslu.switchbottomnavigaiton.screen.home.HomeScreen
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ynemreuslu.switchbottomnavigaiton", appContext.packageName)
    }

    @Test
    fun exampleHomeFragmentCreatedTest() {
        val scenario = launchFragment<HomeScreen>(
            initialState = Lifecycle.State.CREATED
        )
        scenario.moveToState(Lifecycle.State.CREATED)
    }




}