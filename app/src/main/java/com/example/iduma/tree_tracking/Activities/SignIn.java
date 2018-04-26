package com.example.iduma.tree_tracking.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iduma.tree_tracking.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class SignIn extends AppCompatActivity {

    private AppCompatActivity activity = SignIn.this;
    private TextView tvReg, tvLogin;
    private EditText etPhone1, etPassword1;
    private Button btnLogin1;
    private KProgressHUD hud;

    private ProgressDialog progressDialog;
//    private NetworkUtil networkUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Parse.initialize(this);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            goToHomeActivity();

        }

//        networkUtil = new NetworkUtil();

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

                loginUser();

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

    public void loginUser() {
        String username = etPhone1.getText().toString().trim();
        String password = etPassword1.getText().toString().trim();

//        if (networkUtil.isNetworkAvailable(activity)) {

            if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
                Toast.makeText(activity, "Enter your phone number and password", Toast.LENGTH_SHORT).show();

            } else {

                hud = KProgressHUD.create(activity)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait")
                        .setDetailsLabel("Validating User...")
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .setBackgroundColor(Color.BLACK)
                        .setAutoDismiss(true)
                        .show();

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

//        }
//        else {
//            Toast.makeText(activity, "Check your network", Toast.LENGTH_SHORT).show();
//        }
    }

    private void goToHomeActivity() {
        Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplication(), Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}

