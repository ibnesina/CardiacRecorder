package com.example.cardiacrecorder;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class UpdateDeleteTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testUpdateMeasurement(){
        SystemClock.sleep(5000);
        Espresso.onView(withId(R.id.recyclerView)).perform(longClick());
        SystemClock.sleep(1000);

        onView(withId(R.id.editBtn)).perform(click());
        // Verify if the editactivity is displayed
        onView(withId(R.id.editActivity)).check(matches(isDisplayed()));



        // Perform actions to add a record (e.g., enter values in EditText fields)
        onView(withId(R.id.heartRate)).perform(clearText()).perform(ViewActions.typeText("90"));
        onView(withId(R.id.diastolic)).perform(clearText()).perform(ViewActions.typeText("60"));
        onView(withId(R.id.systolic)).perform(clearText()).perform(ViewActions.typeText("100"));
        onView(withId(R.id.comment)).perform(clearText()).perform(ViewActions.typeText("This is a test record"));
        Espresso.pressBack();

        // Click on the "Save" button
        onView(withId(R.id.saveNoteFabBtn)).perform(click());
        // Go back to the detailsRecord
        Espresso.pressBack();

       // Verify if the detailsRecordActivity is displayed
       onView(withId(R.id.detailsRecord)).check(matches(isDisplayed()));

        Espresso.pressBack();
        // Verify if the MainActivity is displayed
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));

        // Verify if the AllRecordsFragment is visible
        onView(withId(R.id.fragmentContainer))
                .check(matches(isDisplayed()));

        // Verify if the newly added record is displayed in the RecyclerView of AllRecordsFragment
        onView(withId(R.id.recyclerView))
                .check(matches(ViewMatchers.hasDescendant(withText("90"))));



    }
    @Test
    public void testDeleteMeasurement(){
        SystemClock.sleep(5000);
        Espresso.onView(withId(R.id.recyclerView)).perform(longClick());
        SystemClock.sleep(1000);
        onView(withId(R.id.deleteBtn)).perform(click());

    }

}