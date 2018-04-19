package com.example.iduma.tree_tracking.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iduma.tree_tracking.R;
import com.hbb20.CountryCodePicker;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class SignUp extends AppCompatActivity {
    private EditText etFname, etLname, etPassword;
    private EditText etPhone;
    private SearchableSpinner spGender, spAccount;
    private Button btnSignup;
    private TextView reg, login;
    private CountryCodePicker spCountry;
    ProgressBar bar;
    ProgressDialog progressDialog;
    SweetAlertDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Parse.initialize(this);

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
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
       // etCountry = findViewById(R.id.etCountry);
        //etGender = findViewById(R.id.etGender);
        spCountry = findViewById(R.id.spCountry);
        spGender = findViewById(R.id.spGender);
        spAccount = findViewById(R.id.spAccountType);
        reg = findViewById(R.id.tvReg);
        login = findViewById(R.id.tvLogin);
        btnSignup = findViewById(R.id.btnSignup);

        progressDialog = new ProgressDialog(SignUp.this);
        bar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login.setTextColor(getResources().getColor(R.color.white));

                Intent intent = new Intent(SignUp.this, SignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String phone = etPhone.getText().toString().trim();
                String firstName = etFname.getText().toString().trim();
                String lastName = etLname.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                String gender = spGender.getItemAtPosition(spGender.getSelectedItemPosition()).toString();
                String accountType = spAccount.getItemAtPosition(spAccount.getSelectedItemPosition()).toString();
                String country = spCountry.getSelectedCountryName().toString();

                if (TextUtils.isEmpty(phone)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage("Phone number cannot be empty")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                if (TextUtils.isEmpty(firstName)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage("Enter your First name cannot be empty")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage("Last name cannot be empty")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                if (TextUtils.isEmpty(password)|| password.length() < 5) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage("Password cannot be less than 5")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                if (gender.equalsIgnoreCase("Select Gender")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage("Please select your gender")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                if (accountType.equalsIgnoreCase("Select Account Type")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage("Please select your Account type")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                if (country.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage("Please select your Country")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                progressDialog.show();


                //create a parseUser object to create a new user
                final ParseUser user = new ParseUser();
                user.setUsername(phone);
                user.setPassword(password);
                user.put("firstName", firstName);
                user.put("lastName", lastName);
                user.put("country", country);
                user.put("gender", gender);
                user.put("accountType", accountType);

                Log.d("sam", ""+country.toString());
                Log.d("sam", ""+gender.toString());
                Log.d("sam", ""+accountType.toString());
                Log.d("sam", ""+phone.toString());

                // First query to check whether a ParseUser with
                // the given phone number already exists or not
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("username", phone);
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> parseUsers, ParseException e) {
                        if (e == null ) {
                            if (parseUsers.size() > 0) {
                                Intent login  = new Intent(SignUp.this, SignIn.class);
                                startActivity(login);
                            } else {
                                signupUser(user);
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                            builder.setMessage(e.getMessage())
                                    .setTitle("Ooops")
                                    .setPositiveButton("Ok", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

            }


        });


    }

    private void signupUser(ParseUser user) {
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //signup successful!!
                    navigateToHome();
                } else {
                    //fail!!!
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setMessage(e.getMessage())
                            .setTitle("Oops!")
                            .setPositiveButton("Ok", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    private void loginUser(String phone, String password) {
    }

    private void navigateToHome() {
        // Let's go to the Login page
        Intent intent = new Intent(SignUp.this, SignIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
