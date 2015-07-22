package com.timore.appegroup.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.timore.appegroup.MainActivity;
import com.timore.appegroup.R;
import com.timore.appegroup.utils.SuperActivity;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends SuperActivity implements View.OnClickListener {

    private Button facebookBtn;
    private Button twitterBtn;
    private Button googleBtn;
    private Button emailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        super.initToolbar(findViewById(R.id.toolbar));
        initFacebook();
        facebookBtn = (Button) findViewById(R.id.login_with_facebook);
        facebookBtn.setOnClickListener(this);

        twitterBtn = (Button) findViewById(R.id.login_with_twitter);
        twitterBtn.setOnClickListener(this);

        googleBtn = (Button) findViewById(R.id.login_with_google);
        googleBtn.setOnClickListener(this);

        emailBtn = (Button) findViewById(R.id.login_with_email);
        emailBtn.setOnClickListener(this);
        findViewById(R.id.login_skip_button).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
      /*  switch (v.getId()) {
            case R.id.login_with_facebook:
                loginWithFacebook();


                break;
            case R.id.login_with_twitter:
                loginWithTwitter();
                break;
            case R.id.login_with_google:
                loginWithGoogle();
                break;
            case R.id.login_with_email:

                break;
            case R.id.login_skip_button:
                Intent main = new Intent(getBaseContext(), MainActivity.class);
                Utils.startActivity(this, main);
                break;
        }*/
    }

    private void initGoogle() {

    }

    private void initTwitter() {

    }

    private void loginWithTwitter() {
        ParseTwitterUtils.logIn(this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.e("MyApp", "Uh oh. The user cancelled the Twitter login.");
                } else if (user.isNew()) {
                    Log.e("MyApp", "User signed up and logged in through Twitter!");
                } else {
                    Log.e("MyApp", "User logged in through Twitter!");
                    AlertDialog.Builder d = new AlertDialog.Builder(LoginActivity.this);
                    d.setTitle("USER: " + ParseTwitterUtils.getTwitter().getScreenName());
                    d.setMessage("USER MOBILE:" + user.getString("mobile") + "\n EMAIL:" + user.getEmail() + "\n ID: " + user.getObjectId());
                    d.create().show();

                }
            }
        });
    }

    private void initFacebook() {

        // Check if there is a currently logged in user
        // and it's linked to a Facebook account.
        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            // Go to the user info activity
            showUserDetailsActivity();
        }

    }

    private void loginWithFacebook() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in..");
        List<String> permissions = Arrays.asList("public_profile", "email");
        // NOTE: for extended permissions, like "user_about_me", your app must be reviewed by the Facebook team
        // (https://developers.facebook.com/docs/facebook-login/permissions/)

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                progressDialog.dismiss();
                if (user == null) {
                    Log.d("TAG", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("TAG", "User signed up and logged in through Facebook!");
                    showUserDetailsActivity();
                } else {
                    Log.d("TAG", "User logged in through Facebook!");
                    showUserDetailsActivity();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }


    private void showUserDetailsActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
