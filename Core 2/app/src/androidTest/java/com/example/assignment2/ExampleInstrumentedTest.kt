package com.example.assignment2

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() = Intents.init()

    @After
    fun cleanUp() = Intents.release()

    @Test
    fun testBorrowButton() {
        // Test that the "Borrow" button works and opens the DetailsActivity
        onView(withId(R.id.borrowButton)).perform(click())
        Intents.intended(hasComponent(DetailsActivity::class.java.name))

        // Set the value using the slider in DetailsActivity
        onView(withId(R.id.borrowDaysSlider)).perform(swipeRight())

        // Click the "Save" button after setting the value using the slider
        onView(withId(R.id.saveButton)).perform(click())

        // Verify that the due date is displayed correctly
        onView(withId(R.id.DueDate)).check(matches(not(withText("Not borrowed"))))
    }

    @Test
    fun testNextButton(){
        val firstItem = onView(withId(R.id.itemName))
        val firstItemText = firstItem.toString()

        // Test that the "Next" button works and displays the next item
        onView(withId(R.id.nextButton)).apply {
            perform(click())
            check(matches(not(withText(firstItemText))))
        }
    }

    @Test
    fun testCheckBoxes() {
        // Test that the checkboxes for "Used" and "Brand New" can be checked and unchecked
        onView(withId(R.id.usedCheckBox)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.usedCheckBox)).perform(click()).check(matches(not(isChecked())))
        onView(withId(R.id.brandNewCheckBox)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.brandNewCheckBox)).perform(click()).check(matches(not(isChecked())))
    }

    @Test
    fun testItemsDetails() {
        // Click on the item image to open the details screen
        onView(withId(R.id.itemImage)).perform(click())

        // Check various details on the details screen
        onView(withId(R.id.detailItemName)).check(matches(withText("Car 1 - An Italian beauty")))
        onView(withId(R.id.detailTopSpeed)).check(matches(withText("Top Speed: 325km/h")))
        onView(withId(R.id.detailEngine)).check(matches(withText("Engine: V10")))
        onView(withId(R.id.detailWeight)).check(matches(withText("Weight: 1487kg")))
        onView(withId(R.id.detailManufacturer)).check(matches(withText("Manufacturer: Lamborghini")))
    }
}
