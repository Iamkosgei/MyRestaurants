package com.example.brian.myrestaurants;


import android.support.test.rule.ActivityTestRule;

import com.example.brian.myrestaurants.ui.RestaurantsActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsNot.not;

public class RestaurantActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<RestaurantsActivity> activityTestRule =
            new ActivityTestRule<>(RestaurantsActivity.class);

    @Test
    public void listItemClickDisplaysToastWithCorrectRestaurant() {
//        View activityDecorView = activityTestRule.getActivity().getWindow().getDecorView();
//        String restaurantName = "Sweet Hereafter \n Serves great: Vegan Food";
//
//        onData(anything())
//                .inAdapterView(withId(R.id.listView))
//                .atPosition(0)
//                .perform(click());
//
//        onView(withText(restaurantName)).inRoot(withDecorView(not(activityDecorView)))
//                .check(matches(withText(restaurantName)));

    }
}
