package com.example.brian.myrestaurants;

import android.os.Build;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import static junit.framework.Assert.assertTrue;


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {
private MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

     @Test
    public void validateTextViewContent() {
         TextView appNameTextView = (TextView) activity.findViewById(R.id.appNameTextView);
         assertTrue("MyRestaurants".equals(appNameTextView.getText().toString()));
    }
}
