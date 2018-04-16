package com.example.iduma.tree_tracking.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tree);

        addTreeImage = (ImageView)findViewById(R.id.iv_add_tree);
        displayTree =(ImageView)findViewById(R.id.iv_treeImages);
        treeCoordinates = (TextView) findViewById(R.id.tv_tree_coordinates);
        reporterName = (TextView)findViewById(R.id.tv_person_name);
        uNoofTrees = (EditText)findViewById(R.id.et_noof_trees);


        addTreeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);

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

}
