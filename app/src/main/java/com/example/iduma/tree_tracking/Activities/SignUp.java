package com.example.iduma.tree_tracking.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.iduma.tree_tracking.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class SignUp extends AppCompatActivity {
    private EditText etFname, etLname, etPhone, etPassword, etCountry, etGender;
    private Spinner spGender, spAccount;
    private Button btnSignup;

    ProgressBar bar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Parse.initialize(this);

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("tree-tracking")
                .clientKey(null)
                .clientBuilder(builder)
                .enableLocalDataStore()
                .server("https://tree-app.herokuapp.com/parse").build());


        etFname = findViewById(R.id.etFname);
        etLname = findViewById(R.id.etLname);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etCountry = findViewById(R.id.etCountry);
        //etGender = findViewById(R.id.etGender);
        spGender = findViewById(R.id.spGender);
        spAccount = findViewById(R.id.spAccoutType);
        btnSignup = findViewById(R.id.btnSignup);

        progressDialog = new ProgressDialog(SignUp.this);
        bar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = etPhone.getText().toString().trim();
                String firstName = etFname.getText().toString().trim();
                String lastName = etLname.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String country = etCountry.getText().toString().trim();
                //String gender = etGender.getText().toString().trim();

                String gender = spGender.getItemAtPosition(spGender.getSelectedItemPosition()).toString();
                String accountType = spAccount.getItemAtPosition(spAccount.getSelectedItemPosition()).toString();

                if (TextUtils.isEmpty(phone)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage("Phone number cannot be empty")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                //create a parseUser object to create a new user
                final ParseUser user = new ParseUser();
                user.setUsername(phone);
                user.setPassword(password);
                user.put("firstName", firstName);
                user.put("lastName", lastName);
                user.put("country", country);
                user.put("gender", gender);
                user.put("accountType", accountType);

                // First query to check whether a ParseUser with
                // the given phone number already exists or not
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("username", phone);

                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {

                            //user already exist? the login
                            if (objects.size() > 0) {
                                //loginUser(phone, "phone");
                                Intent reg = new Intent(SignUp.this, SignIn.class);
                                startActivity(reg);
                                finish();
                            }else {
                                //no user found, so signup
                                signupUser(user);
                            }

                        }

                        else {
                            //shit happened
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                            builder.setMessage(e.getMessage())
                                    .setTitle("Oops!")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }
                });
            }

            private void signupUser(ParseUser user) {
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // Signup successful!

                            navigateToHome();
                        } else {
                            // Fail!
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                            builder.setMessage(e.getMessage())
                                    .setTitle("Oops!")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
            }
        });


    }

    private void navigateToHome() {
        // Let's go to the Login page
        Intent intent = new Intent(SignUp.this, SignIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
