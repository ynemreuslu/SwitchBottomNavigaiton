package com.ynemreuslu.switchbottomnavigaiton

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {

        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testSwitchHappinessChecked() {
        onView(withId(R.id.switchHappiness))
            .check(matches(not(isClickable())))
    }

    @Test
    fun testTextViewVisibilityAfterEnablingSwitch() {
        onView(withId(R.id.switchGiving)).perform(click());

        onView(withText(R.string.giving))
            .check(matches(isDisplayed()))
    }
    @Test
    fun testRemoveMenuItem() {

        onView(withId(R.id.switchRespect))
            .perform(click())

        onView(withId(R.id.bottomNavigationView))
            .check(matches(hasDescendant(withText(R.id.respectScreen))))


        onView(withId(R.id.switchRespect))
            .perform(click())


        onView(withId(R.id.bottomNavigationView))
            .check(matches(not(hasDescendant(withText(R.id.respectScreen)))))
    }

    @Test
    fun testMaxItemsExceededMessage() {
        onView(withId(R.id.switchHappiness)).perform(click())
        onView(withId(R.id.switchGiving)).perform(click())
        onView(withId(R.id.switchRespect)).perform(click())
        onView(withId(R.id.switchKindness)).perform(click())
        onView(withId(R.id.switchOptimism)).perform(click())
        onView(withId(R.id.switchHappiness)).perform(click())
    }

}