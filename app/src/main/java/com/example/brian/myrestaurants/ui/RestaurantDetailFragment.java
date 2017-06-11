package com.example.brian.myrestaurants.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brian.myrestaurants.Constants;
import com.example.brian.myrestaurants.R;
import com.example.brian.myrestaurants.models.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class    RestaurantDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.restaurantImageView)
    ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView)
    TextView mNameLabel;
    @BindView(R.id.cuisineTextView)
    TextView mCategoriesLabel;
    @BindView(R.id.ratingTextView)
    TextView mRatingLabel;
    @BindView(R.id.websiteTextView)
    TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView)
    TextView mPhoneLabel;
    @BindView(R.id.addressTextView)
    TextView mAddressLabel;
    @BindView(R.id.saveRestaurantButton)
    Button mSaveRestaurantsButton;

    private Restaurant mRestaurant;

    public static RestaurantDetailFragment newInstance(Restaurant restaurant) {
        RestaurantDetailFragment restaurantDetailFragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("restaurant", Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestaurant = Parcels.unwrap(getArguments().getParcelable("restaurant"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);

        if (mRestaurant.getImageUrl() != null) {
            Picasso.with(view.getContext()).load(mRestaurant.getImageUrl())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.restaurant_placeholder)
                    .into(mImageLabel);
        }


        if (mRestaurant.getName() != null)
            mNameLabel.setText(mRestaurant.getName());
        if (mRestaurant.getCategories() != null)
            mCategoriesLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getCategories()));
        if (mRestaurant.getRating() != null)
            mRatingLabel.setText(String.format(Locale.getDefault(), "%.2f/5", mRestaurant.getRating()));
        if (mRestaurant.getPhone() != null)
            mPhoneLabel.setText(mRestaurant.getPhone());
        if (mRestaurant.getLocation() != null)
            mAddressLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getLocation().getDisplayAddress()));

        mWebsiteLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mSaveRestaurantsButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRestaurant.getWebsite()));
            startActivity(webIntent);
        }

        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                                            Uri.parse("tel:" + mRestaurant.getPhone()));
            startActivity(phoneIntent);
        }

        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("geo:" + mRestaurant.getCoordinates().getLatitude()
                                                    + "," + mRestaurant.getCoordinates().getLongitude()
                                                    + "?q=(" + mRestaurant.getName() + ")" ));
            startActivity(mapIntent);
        }

        if (v == mSaveRestaurantsButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference restaurantRef = FirebaseDatabase.getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                    .child(uid);

            DatabaseReference pushRef = restaurantRef.push();
            String pushId = pushRef.getKey();
            mRestaurant.setPushId(pushId);
            pushRef.setValue(mRestaurant);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }

    }
}
