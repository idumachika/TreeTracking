package com.example.iduma.tree_tracking.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.iduma.tree_tracking.R;

public class SignIn extends AppCompatActivity {

    private TextView tvReg, tvLogin;
    private EditText etPhone1, etPassword1;
    private Button btnLogin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvReg = findViewById(R.id.tvReg);
        tvLogin = findViewById(R.id.tvLogin);
        etPassword1 = findViewById(R.id.etPassword1);
        etPhone1 = findViewById(R.id.etPhone1);
        btnLogin1 = findViewById(R.id.btnLogin1);

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
}
