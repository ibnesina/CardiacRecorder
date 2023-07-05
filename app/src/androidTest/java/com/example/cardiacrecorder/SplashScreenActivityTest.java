package com.example.cardiacrecorder;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class SplashScreenActivityTest {
    @Rule
    public ActivityScenarioRule<SplashScreenActivity> activityRule = new ActivityScenarioRule<>(SplashScreenActivity.class);

    @Test
    public void testAppName() {
        onView(withText("CardiacRecorder")).check(matches(isDisplayed())); //Check the name on the screen
    }


    @Test
    public void testSplashScreenDisplayedOrNot() {
        onView(withId(R.id.splash_screen)).check(matches(isDisplayed()));//Check the name on the screen

    }
}