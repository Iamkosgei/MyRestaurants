package com.example.brian.myrestaurants.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.brian.myrestaurants.R;
import com.example.brian.myrestaurants.models.Restaurant;
import com.example.brian.myrestaurants.services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RestaurantsActivity extends AppCompatActivity {
    @BindView(R.id.locationTextView) TextView mLocationTextView;
    @BindView(R.id.listView) ListView mListView;

    public ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private static final String TAG = RestaurantsActivity.class.getSimpleName();
    private String[] restaurants = new String[] {
            "Sweet Hereafter", "Cricket", "Hawthorne Fish House",
            "Viking Soul Food", "Red Square", "Horse Brass",
            "Dick's Kitchen", "Taco Bell", "Me Kha Noodle Bar",
            "La Bonita Taqueria", "Smokehouse Tavern", "Pembiche",
            "Kay's Bar", "Gnarly Grey", "Slappy Cakes", "Mi Mero Mole"
    };

    private String[] cuisines = new String[] {
            "Vegan Food", "Breakfast", "Fishs Dishs",
            "Scandinavian", "Coffee", "English Food",
            "Burgers", "Fast Food", "Noodle Soups",
            "Mexican", "BBQ", "Cuban",
            "Bar Food", "Sports Bar", "Breakfast", "Mexican"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        Log.d(TAG, "zipCode: " + location);
        getRestaurants(location);
        mLocationTextView.setText("Here are all the restaurants near: " + location);
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

                RestaurantsActivity.this.runOnUiThread(() -> {
                    String[] restaurantNames = new String[mRestaurants.size()];

                    for (int i = 0; i < restaurantNames.length; i++) {
                        restaurantNames[i] = mRestaurants.get(i).getmName();
                    }

                    ArrayAdapter adapter = new ArrayAdapter(RestaurantsActivity.this,
                            android.R.layout.simple_list_item_1, restaurantNames);
                    mListView.setAdapter(adapter);

                    for (Restaurant restaurant : mRestaurants) {
                        Log.d(TAG, "Name: " + restaurant.getmName());
                        Log.d(TAG, "Phone: " + restaurant.getmPhone());
                        Log.d(TAG, "Website: " + restaurant.getmWebsite());
                        Log.d(TAG, "Image url: " + restaurant.getmImageUrl());
                        Log.d(TAG, "Rating: " + Double.toString(restaurant.getmRating()));
                        Log.d(TAG, "Address: " + android.text.TextUtils.join(", ", restaurant.getmAddress()));
                        Log.d(TAG, "Categories: " + restaurant.getmCategories().toString());
                    }
                });
            }
        });
    }
}
