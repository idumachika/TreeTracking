package com.example.iduma.tree_tracking.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iduma.tree_tracking.R;

public class AddTree extends AppCompatActivity {
    private ImageView addTreeImage;
    private ImageView displayTree;
    private TextView treeCoordinates;
    private TextView reporterName;
    private EditText uNoofTrees;
    private Button submitTrees;
    private static final int CAMERA_REQUEST_CODE = 1;
    private int CAMERA_PERMISSION_CODE=24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tree);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            },CAMERA_PERMISSION_CODE);

        }

        addTreeImage = (ImageView)findViewById(R.id.iv_add_tree);
        displayTree =(ImageView)findViewById(R.id.iv_treeImages);
        treeCoordinates = (TextView) findViewById(R.id.tv_tree_coordinates);
        reporterName = (TextView)findViewById(R.id.tv_person_name);
        uNoofTrees = (EditText)findViewById(R.id.et_noof_trees);
        submitTrees = (Button)findViewById(R.id.submit_tree);


        addTreeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);

            }
        });

        setUi();

        submitTrees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noofTrees = uNoofTrees.getText().toString().trim();
                if (TextUtils.isEmpty(noofTrees)||noofTrees=="0"){
                    Toast.makeText(getApplicationContext(),"No of Trees cannot be empty",
                            Toast.LENGTH_LONG).show();
                } else{

                }

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            displayTree.setImageBitmap(bitmap);

        }

    }

    public void setUi(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(
                "userinfo", Activity.MODE_PRIVATE);

        String reporterFirst = preferences.getString("firstName", "firstName");
        String reporterSecond=preferences.getString("lastName","lastName");
        reporterName.setText(reporterFirst+" "+reporterSecond);
        String coordinates = preferences.getString("coordinates","coordinates");
        treeCoordinates.setText(coordinates);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted ",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }


    }

}
