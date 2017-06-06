package com.example.brian.myrestaurants.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.brian.myrestaurants.Constants;
import com.example.brian.myrestaurants.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Optional;

import butterknife.ButterKnife;
import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.findRestaurantsButton)
    Button mFindRestaurantsButton;
    @BindView(R.id.savedRestaurantsButton)
    Button mSavedRestaurantsButton;
    @BindView(R.id.locationEditText)
    EditText mLocationEditText;
    @BindView(R.id.appNameTextView)
    TextView mAppNameTextView;

//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;
    private DatabaseReference mSearchedLocationReference;
    private ValueEventListener mSearchedLOcationReferenceListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedLocationReference = FirebaseDatabase.getInstance()
            .getReference()
            .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

        mSearchedLOcationReferenceListener =
                mSearchedLocationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapShot :
                        dataSnapshot.getChildren()) {
                    String location = locationSnapShot.getValue().toString();
                    Log.d("Locations updated", "location: " + location);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface windsongFont = Typeface.createFromAsset(getAssets(), "fonts/Windsong.ttf");
        mAppNameTextView.setTypeface(windsongFont);

//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();


        mFindRestaurantsButton.setOnClickListener(this);
        mSavedRestaurantsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFindRestaurantsButton) {
            String location = mLocationEditText.getText().toString();
            saveLocationToFirebase(location);
//            if (!(location.isEmpty())) {
//                addToSharedPreferences(location);
//            }

            Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);

            intent.putExtra("location", location);
            startActivity(intent);
        }

        if (v == mSavedRestaurantsButton) {
            Intent intent  = new Intent(MainActivity.this, SavedRestaurantListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedLocationReference.removeEventListener(mSearchedLOcationReferenceListener);
    }

    private void saveLocationToFirebase(String location) {
        mSearchedLocationReference.push().setValue(location);
    }

    private void addToSharedPreferences(String location) {
//        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
}
