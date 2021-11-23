package com.example.myapplication

import android.content.pm.ActivityInfo
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAbout() {
        openAbout()
        onView(withId(R.id.activity_about))
            .check(matches(isDisplayed()))
    }

    private fun existFr1() {
        //check exists fr1
        onView(withId(R.id.fragment1)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment2)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(doesNotExist())
        onView(withId(R.id.bnToFirst)).check(doesNotExist())
        onView(withId(R.id.bnToSecond)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToThird)).check(doesNotExist())
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    private fun existFr2() {
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment2)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment3)).check(doesNotExist())
        onView(withId(R.id.bnToFirst)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToSecond)).check(doesNotExist())
        onView(withId(R.id.bnToThird)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    private fun existFr3() {
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment2)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToFirst)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToSecond)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToThird)).check(doesNotExist())
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    private fun existActivityAbout() {
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment2)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(doesNotExist())
        onView(withId(R.id.bnToFirst)).check(doesNotExist())
        onView(withId(R.id.bnToSecond)).check(doesNotExist())
        onView(withId(R.id.bnToThird)).check(doesNotExist())
        onView(withId(R.id.nav_view)).check(doesNotExist())
        onView(withId(R.id.tvAbout))
    }
    @Test
    fun testExists() {
        existFr1()
        onView(withId(R.id.bnToSecond)).perform(click())
        //check exists fr2
        existFr2()
        onView(withId(R.id.bnToThird)).perform(click())
        //check exists fr3
        existFr3()
        openAbout()
        //check exists fr about
        existActivityAbout()
    }

    @Test
    fun testBackStack() {
        onView(withId(R.id.bnToSecond)).perform(click())
        openAbout()
        pressBack()
        onView(withId(R.id.bnToFirst)).perform(click())
        openAbout()
        pressBack()
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        openAbout()
        pressBack()
        onView(withId(R.id.bnToSecond)).perform(click())
        openAbout()
        pressBack()
        onView(withId(R.id.bnToThird)).perform(click())
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        onView(withId(R.id.bnToFirst)).perform(click())
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        openAbout()
        pressBack()
        pressBack()
        pressBack()
        pressBackUnconditionally()
        assertTrue(activityScenarioRule.scenario.state.isAtLeast(Lifecycle.State.DESTROYED))
    }


    @Test
    fun testFragmentsExists() {
        openAbout()
        onView(withId(R.id.tvAbout)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_about)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.fragment1)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToSecond)).perform(click())
        openAbout()
        onView(withId(R.id.tvAbout)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_about)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.fragment2)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToThird)).perform(click())
        openAbout()
        onView(withId(R.id.tvAbout)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_about)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.fragment3)).check(matches(isDisplayed()))
        openAbout()
        onView(withId(R.id.tvAbout)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_about)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.fragment3)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.fragment2)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.fragment1)).check(matches(isDisplayed()))
    }


    @Test
    fun testUpNavigation() {
        existFr1()
        openAbout()
        existActivityAbout()
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description)).perform(click())
        onView(withId(R.id.bnToSecond)).perform(click())
        existFr2()
        openAbout()
        existActivityAbout()
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description)).perform(click())
        existFr2()
        onView(withId(R.id.bnToThird)).perform(click())
        existFr3()
        openAbout()
        existActivityAbout()
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description)).perform(click())
        existFr3()
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description)).perform(click())
        existFr2()
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description)).perform(click())
        existFr1()

        try {
            onView(withContentDescription(R.string.nav_app_bar_navigate_up_description)).perform(
                click()
            )
            assert(false)
        } catch (NoActivityResumedException: Exception) {
            assert(true)
        }
    }


    @Test
    fun testRotation() {
        openAbout()
        testRotationAbout()
        pressBack()
        testRotation1()
        onView(withId(R.id.bnToSecond)).perform(click())
        testRotation2()
        onView(withId(R.id.bnToThird)).perform(click())
        testRotation3()
    }

    private fun testRotationAbout() {
        existActivityAbout()

        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        Thread.sleep(1000)

        existActivityAbout()

        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        Thread.sleep(1000)
    }

    private fun testRotation1() {
        existFr1()

        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        Thread.sleep(1000)

        existFr1()

        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        Thread.sleep(1000)
    }

    private fun testRotation2() {
        existFr2()

        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        Thread.sleep(1000)

        existFr2()

        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        Thread.sleep(1000)
    }

    private fun testRotation3() {
        existFr3()

        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        Thread.sleep(1000)

        existFr3()

        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

}
