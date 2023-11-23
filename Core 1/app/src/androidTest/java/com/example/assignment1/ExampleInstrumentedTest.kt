package com.example.assignment1

import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DiceRollerActivityTest {

    private var myAddButton = 0
    private var mySubtractButton = 0
    private var myRollButton = 0
    private var myResetButton = 0
    private var myScoreTextField = 0

    @Before
    fun initValidString() {
        // Please set your id names here.
        myAddButton = R.id.button3
        mySubtractButton = R.id.button4
        myRollButton = R.id.button2
        myResetButton = R.id.button5
        myScoreTextField = R.id.textView
    }

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(DiceRollerActivity::class.java)

    @Test
    fun clickRollAddButton3Times() {
        // given: open app
        // when: roll and add three times
        // then: score is 10
        val addButton = onView(withId(myAddButton))
        val rollButton = onView(withId(myRollButton))

        for (i in 1..3) {
            rollButton.perform(click())
            addButton.perform(click())
        }

        val textView = onView(withId(myScoreTextField))
        textView.check(matches(withText("\n10")))
    }

    @Test
    fun clickRollAddSubtractButton3Times() {
        // given: open app
        // when: click add, subtract, add
        // then: score is 8
        val addButton = onView(withId(myAddButton))
        val subtractButton = onView(withId(mySubtractButton))
        val rollButton = onView(withId(myRollButton))

        rollButton.perform(click())
        addButton.perform(click())
        rollButton.perform(click())
        subtractButton.perform(click())
        rollButton.perform(click())
        addButton.perform(click())

        val textView = onView(withId(myScoreTextField))
        textView.check(matches(withText("\n8")))
    }

    @Test
    fun testLowerLimitsOfScore() {
        // given: open app
        // when: click add, subtract, subtract
        // then: score is 0
        val addButton = onView(withId(myAddButton))
        val subtractButton = onView(withId(mySubtractButton))
        val rollButton = onView(withId(myRollButton))

        rollButton.perform(click())
        addButton.perform(click())
        rollButton.perform(click())
        subtractButton.perform(click())
        rollButton.perform(click())
        subtractButton.perform(click())

        val textView = onView(withId(myScoreTextField))
        textView.check(matches(withText("\n0")))
    }

    @Test
    fun testResetButton() {
        // given: open app
        // when: click roll/add 3 times and reset 1 time
        // then: score is 0
        val addButton = onView(withId(myAddButton))
        val resetButton = onView(withId(myResetButton))
        val rollButton = onView(withId(myRollButton))

        for (i in 1..3) {
            rollButton.perform(click())
            addButton.perform(click())
        }

        resetButton.perform(click())

        val textView = onView(withId(myScoreTextField))
        textView.check(matches(withText("\n0")))
    }

    @Test
    fun testScoreOnRotation() {
        // given: open app
        // when: click roll/add 3 times and rotate device
        // then: score is 10
        val addButton = onView(withId(myAddButton))
        val rollButton = onView(withId(myRollButton))

        for (i in 1..3) {
            rollButton.perform(click())
            addButton.perform(click())
        }

        val textView = onView(withId(myScoreTextField))
        textView.check(matches(withText("\n10")))

        mActivityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        textView.check(matches(withText("\n10")))

    }

    @Test
    fun testScoreOnRotationWithClick() {
        // given: open app
        // when: click roll/add 2 times and rotate device, click roll/add
        // then: score is 9
        val addButton = onView(withId(myAddButton))
        val rollButton = onView(withId(myRollButton))

        for (i in 1..2) {
            rollButton.perform(click())
            addButton.perform(click())
        }

        val textView = onView(withId(myScoreTextField))
        textView.check(matches(withText("\n5")))

        mActivityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        rollButton.perform(click())
        addButton.perform(click())
        val textView2 = onView(withId(myScoreTextField))
        textView2.check(matches(withText("\n9")))

    }


    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}