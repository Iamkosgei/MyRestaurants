package com.example.brian.myrestaurants.services;

import com.example.brian.myrestaurants.Constants;
import com.example.brian.myrestaurants.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class YelpService {
    private static OkHttpClient client = new OkHttpClient();

    public static void findRestaurants(String location, Callback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + Constants.YELP_ACCESS_TOKEN)
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Restaurant> processResults(Response response) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try {
            String jsonData = response.body().string();

            if (response.isSuccessful()) {
                JSONObject yelpJSON = new JSONObject(jsonData);
                JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
                for (int i = 0; i < businessesJSON.length(); i++) {
                    JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
                    String name = restaurantJSON.getString("name");
                    String phone = restaurantJSON.optString("display_phone", "Phone not available");
                    String website = restaurantJSON.getString("url");
                    double rating = restaurantJSON.getDouble("rating");
                    String imageUrl = restaurantJSON.getString("image_url");
                    double latitude = restaurantJSON.getJSONObject("coordinates")
                            .getDouble("latitude");
                    double longitude = restaurantJSON.getJSONObject("coordinates")
                            .getDouble("longitude");

                    ArrayList<String> address = new ArrayList<>();
                    JSONArray addressJSON = restaurantJSON.getJSONObject("location")
                            .getJSONArray("display_address");
                    for (int j = 0; j < addressJSON.length(); j++) {
                        address.add(addressJSON.get(j).toString());
                    }

                    ArrayList<String> categories = new ArrayList<>();
                    JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");
                    for (int j = 0; j < categoriesJSON.length(); j++) {
                        categories.add(categoriesJSON.getJSONObject(j).getString("title"));
                    }

                    Restaurant restaurant = new Restaurant(name, phone, website, rating, imageUrl,
                            address, latitude, longitude, categories);
                    restaurants.add(restaurant);
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return restaurants;
    }
}
