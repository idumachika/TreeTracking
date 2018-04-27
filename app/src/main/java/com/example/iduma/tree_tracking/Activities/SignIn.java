package com.example.iduma.tree_tracking.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iduma.tree_tracking.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class SignIn extends AppCompatActivity {

    private TextView tvReg, tvLogin;
    private EditText etPhone1, etPassword1;
    private Button btnLogin;
    private String username, password;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvReg = findViewById(R.id.tvReg);
        tvLogin = findViewById(R.id.tvLogin);
        etPassword1 = findViewById(R.id.etPassword1);
        etPhone1 = findViewById(R.id.etPhone1);
        btnLogin = findViewById(R.id.btnLogin1);

        progressDialog = new ProgressDialog(SignIn.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                username = etPhone1.getText().toString();
                password = etPassword1.getText().toString();




//                    Log.d("username: ", ""+etPhone1.getText().toString());
                    //Log.d("password: ", ""+etpassword1);
                    try {
                        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

                        ParseUser.logInInBackground(username, password, new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    goToHomeActivity();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
                                    builder.setMessage(e.getMessage())
                                            .setTitle("Oops!!!")
                                            .setPositiveButton("Ok", null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }
                        });

                        } else {
                            Toast.makeText(SignIn.this, "Enter your phone number and password",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (NullPointerException e) {
                        Log.d("Null: ", e.getMessage());
                    }






            }
        });

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(SignIn.this, SignUp.class);
                reg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(reg);
            }
        });
    }

//    private void login( ) {
//
//        phone = etPhone1.getText().toString().trim();
//        password = etPassword1.getText().toString().trim();
//
//
//
//    }

    private void goToHomeActivity() {
        Intent intent = new Intent(getApplication(), Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}

