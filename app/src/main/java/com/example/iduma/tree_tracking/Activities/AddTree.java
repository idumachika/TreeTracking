package com.example.iduma.tree_tracking.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iduma.tree_tracking.R;

public class AddTree extends AppCompatActivity {
    private ImageView addTreeImage;
    private ImageView displayTree;
    private TextView treeCoordinates;
    private TextView reporterName;
    private EditText uNoofTrees;
    private static final int CAMERA_REQUEST_CODE = 1;
    private String firstname, lastname;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tree);

        addTreeImage = (ImageView)findViewById(R.id.iv_add_tree);
        displayTree =(ImageView)findViewById(R.id.iv_treeImages);
        treeCoordinates = (TextView) findViewById(R.id.tv_tree_coordinates);
        reporterName = (TextView)findViewById(R.id.tv_person_name);
        uNoofTrees = (EditText)findViewById(R.id.et_noof_trees);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            firstname = bundle.getString("firstname");
            lastname = bundle.getString("lastname");
            latitude = bundle.getDouble("lat");
            longitude = bundle.getDouble("long");

            Log.d("lat...", "" +latitude);
            Log.d("long...", "" +longitude);
        }

        addTreeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);

            }
        });

        treeCoordinates.setText(latitude + ", " + longitude);
        reporterName.setText(lastname.concat(" ").concat(firstname));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            displayTree.setImageBitmap(bitmap);

        }

    }

}
