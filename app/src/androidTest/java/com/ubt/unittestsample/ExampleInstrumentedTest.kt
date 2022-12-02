package com.ubt.unittestsample

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.ubt.unittestsample.espresso.EspressoTestActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    @get:Rule
    val mActivityRule = ActivityTestRule(EspressoTestActivity::class.java)

    @Before
    fun startActivity() {
        mActivityRule.launchActivity(Intent())
    }

    @Test
    fun useAppContext() {
//        // Context of the app under test.
        onView(withId(R.id.tv_main)).check(matches(isFocused()))
        onView(withId(R.id.tv_main)).perform(click())
    }
}