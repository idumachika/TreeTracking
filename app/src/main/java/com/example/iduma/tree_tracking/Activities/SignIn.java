package com.example.iduma.tree_tracking.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iduma.tree_tracking.R;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class SignIn extends AppCompatActivity {

    private TextView tvReg, tvLogin;
    private EditText etPhone1, etPassword1;
    private Button btnLogin1;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Parse.initialize(this);

        tvReg = findViewById(R.id.tvReg);
        tvLogin = findViewById(R.id.tvLogin);
        etPassword1 = findViewById(R.id.etPassword1);
        etPhone1 = findViewById(R.id.etPhone1);
        btnLogin1 = findViewById(R.id.btnLogin1);

        progressDialog = new ProgressDialog(SignIn.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);

        btnLogin1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

              login();


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

    private void login( ) {
        String username = etPhone1.getText().toString().trim();
        String password = etPassword1.getText().toString().trim();

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

    }

    private void goToHomeActivity() {
        Intent intent = new Intent(getApplication(), Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}

