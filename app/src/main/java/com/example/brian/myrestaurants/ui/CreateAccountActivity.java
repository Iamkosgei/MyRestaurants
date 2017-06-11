package com.example.brian.myrestaurants.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brian.myrestaurants.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.createUserButton)
    Button mCreateUserButton;
    @BindView(R.id.nameEditText)
    EditText mNameEditText;
    @BindView(R.id.emailEditText)
    EditText mEmailEditText;
    @BindView(R.id.passwordEditText)
    EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText)
    EditText mConfirmPasswordEditText;
    @BindView(R.id.loginTextView)
    TextView mLoginTextView;

    public static final String TAG = CreateAccountActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressDialog mAuthProgressDialog;

    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        
        mLoginTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);

        createAuthStateListener();
        createAuthProgressDialog();
    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void createAuthStateListener() {
        mAuthStateListener = firebaseAuth -> {
            final FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginTextView) {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);

          /*
          We don't actually want users navigating back to CreateAccountActivity after they've
          already created an account. They should no longer have any use for this form if they
          already have an account. To prevent this, we can explicitly remove these activities
          from our back stack altogether by setting flags on our intent.

          Intent flags are essentially just extra pieces of information optionally added
          to an intent that determine how the specific intent is handled by Android.

          FLAG_ACTIVITY_CLEAR_TASK will cause any existing task that would be associated
          with the activity to be cleared before the activity is started.

          FLAG_ACTIVITY_NEW_TASK will make the activity we are navigating to the
          start of a brand new task on this history stack.
          */

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        
        if (v == mCreateUserButton) {
            createNewUser();
        }
        
    }

    private void createNewUser() {
        mName = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();
        boolean validName = isValidName(mName);

        if (!validName
                || !isValidEmail(email)
                || !isValidPassword(password, confirmPassword)) {
            return;
        }

        mAuthProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {

                    mAuthProgressDialog.dismiss();

                    if (task.isSuccessful()) {
                        Log.d(TAG, "Authentication successful");
                        createFirebaseUserProfile(task.getResult().getUser());
                    } else {
                        Toast.makeText(CreateAccountActivity.this, "Authentication failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createFirebaseUserProfile(final FirebaseUser user) {
        UserProfileChangeRequest addProfileName
                = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(mName)
                                            .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, user.getDisplayName());
                    }
                });
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());

        if (!isGoodEmail) {
            mEmailEditText.setError("Please enter a valid email address");
        }

        return isGoodEmail;
    }

    private boolean isValidName(String name) {
        if (name.isEmpty()) {
            mNameEditText.setError("Please enter your name");
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mPasswordEditText.setError("Please create a password containing at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mPasswordEditText.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}
