package com.example.brian.myrestaurants;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.findRestaurantsButton) Button mFindRestaurantsButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface ostrichFont = Typeface.createFromAsset(getAssets(), "fonts/ostrich-regular.ttf");
        Typeface windsongFont = Typeface.createFromAsset(getAssets(), "fonts/Windsong.ttf");

        mAppNameTextView.setTypeface(windsongFont);

        mFindRestaurantsButton.setOnClickListener(v -> {
            String location = mLocationEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, RestaurantsActivity.class);

            intent.putExtra("location", location);
            startActivity(intent);
        });
    }
}
