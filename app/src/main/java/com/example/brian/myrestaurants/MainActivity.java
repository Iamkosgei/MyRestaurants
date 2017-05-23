package com.example.brian.myrestaurants;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button mFindRestaurantsButton;
    private EditText mLocationEditText;
    private TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFindRestaurantsButton = (Button) findViewById(R.id.findRestaurantsButton);
        mLocationEditText = (EditText) findViewById(R.id.locationEditText);

        Typeface ostrichBoldFont = Typeface.createFromAsset(getAssets(), "fonts/OstrichSans-Bold.otf");
        Typeface windsongFont = Typeface.createFromAsset(getAssets(), "fonts/Windsong.ttf");
        mAppNameTextView = (TextView) findViewById(R.id.appNameTextView);
        mAppNameTextView.setTypeface(windsongFont);

        mFindRestaurantsButton.setOnClickListener(v -> {
            String location = mLocationEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, RestaurantsActivity.class);
            intent.putExtra("location", location);
            startActivity(intent);
        });
    }
}
