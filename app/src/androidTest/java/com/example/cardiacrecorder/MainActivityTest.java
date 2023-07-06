package com.example.cardiacrecorder;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAppName() {
        onView(withText("CardiacRecorder")).check(matches(isDisplayed())); //Check the name on the screen
    }


    @Test
    public void testMainActivityDisplayedOrNot() {
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()));//Check the name on the screen

    }
    @Test
    public void testToolbarDisplayedOrNot() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));//Check the name on the screen
    }


    // Verify if the AllRecordsFragment is visible
    @Test
    public void testfragmentcontainerDisplayedOrNot() {
    Espresso.onView(ViewMatchers.withId(R.id.fragmentContainer))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void testAddingRecords() {
        // Verify if the MainActivity is displayed
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));

        // Click on the "Create Record" button
        onView(withId(R.id.createNoteFabBtn)).perform(click());


        // Verify if the CreateNote activity is launched
        onView(withId(R.id.saveNoteFabBtn)).check(matches(isDisplayed()));

        // Perform actions to add a record (e.g., enter values in EditText fields)
        onView(withId(R.id.heartRate)).perform(ViewActions.typeText("80"));
        onView(withId(R.id.diastolic)).perform(ViewActions.typeText("70"));
        onView(withId(R.id.systolic)).perform(ViewActions.typeText("120"));
        onView(withId(R.id.comment)).perform(ViewActions.typeText("This is a test record"));
        Espresso.pressBack();

        // Click on the "Save" button
        onView(withId(R.id.saveNoteFabBtn)).perform(click());
        Espresso.pressBack();

        // Verify if the MainActivity is displayed
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));

        // Verify if the AllRecordsFragment is visible
        onView(withId(R.id.fragmentContainer))
                .check(matches(isDisplayed()));

        // Verify if the newly added record is displayed in the RecyclerView of AllRecordsFragment
        onView(withId(R.id.recyclerView))
                .check(matches(ViewMatchers.hasDescendant(withText("80"))));





}




}