package com.example.brian.myrestaurants.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.brian.myrestaurants.Constants;
import com.example.brian.myrestaurants.R;
import com.example.brian.myrestaurants.adapters.RestaurantListAdapter;
import com.example.brian.myrestaurants.models.Restaurant;
import com.example.brian.myrestaurants.services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RestaurantListActivity extends AppCompatActivity {
    @BindView(R.id.locationTextView) TextView mLocationTextView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final String TAG = RestaurantListActivity.class.getSimpleName();
    private RestaurantListAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private String mRecentAddress;


    public ArrayList<Restaurant> mRestaurants = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);

        if (location.isEmpty() && mRecentAddress != null) {
            getRestaurants(mRecentAddress);
            mLocationTextView.setText("Here are all the restaurants near: " + mRecentAddress);
        } else {
            getRestaurants(location);
            mLocationTextView.setText("Here are all the restaurants near: " + location);
        }
    }

    private void getRestaurants(String location) {
        final YelpService yelpService = new YelpService();
        yelpService.findRestaurants(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mRestaurants = yelpService.processResults(response);

                RestaurantListActivity.this.runOnUiThread(() -> {
                    mAdapter = new RestaurantListAdapter(getApplicationContext(), mRestaurants);

                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager
                            = new LinearLayoutManager(RestaurantListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                });
            }
        });
    }
}
