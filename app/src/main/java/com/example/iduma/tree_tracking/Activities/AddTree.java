package com.example.iduma.tree_tracking.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iduma.tree_tracking.R;
import com.example.iduma.tree_tracking.Utility.Util;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class AddTree extends AppCompatActivity {
    private ImageView addTreeImage;
    private ImageView displayTree;
    private TextView treeCoordinates;
    private TextView reporterName;
    private EditText uNoofTrees;
    private static final int CAMERA_REQUEST_CODE = 1;
    private int CAMERA_PERMISSION_CODE = 24;
    private String firstname, lastname;
    private double latitude, longitude;
    private AppCompatActivity activity = AddTree.this;
    private Util util = new Util();
    private Button submitTree;
    private Spinner spTreeType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tree);

        Parse.initialize(this);

        addTreeImage = findViewById(R.id.iv_add_tree);
        displayTree = findViewById(R.id.iv_treeImages);
        treeCoordinates = findViewById(R.id.tv_tree_coordinates);
        reporterName = findViewById(R.id.tv_person_name);
        uNoofTrees = findViewById(R.id.etNoTrees);
        submitTree = findViewById(R.id.submit_tree);
        spTreeType = findViewById(R.id.spTreeType);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, CAMERA_PERMISSION_CODE);

        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            firstname = bundle.getString("firstname");
            lastname = bundle.getString("lastname");
            latitude = bundle.getDouble("lat");
            longitude = bundle.getDouble("long");

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
        final String noTrees = uNoofTrees.getText().toString();
        final ParseGeoPoint point = new ParseGeoPoint(latitude, longitude);

     //  final int Trees = Integer.parseInt(noTrees);

        submitTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (util.isNetworkAvailable(activity)) {

                    final String noTrees = uNoofTrees.getText().toString();
                    final String treeType = spTreeType.getItemAtPosition(spTreeType.getSelectedItemPosition()).toString();

                        final ParseObject tree = new ParseObject("Trees");
                        tree.put("noTrees", noTrees);
                        tree.put("location", point);
                        tree.put("treeType", treeType);
                       // tree.saveInBackground();

                    Log.d("b4", noTrees);
                    Log.d("b4", point.toString());
                    Log.d("b4", treeType.toString());

                        tree.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (tree.saveInBackground().isCompleted()) {

                                    Log.d("after", noTrees);
                                    Log.d("after", point.toString());
                                    Log.d("after", treeType.toString());

                                    startActivity(new Intent(AddTree.this, Home.class));

                                    Toast.makeText(activity, "suceess", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Log.d("failed", e.getMessage());
                                    Toast.makeText(activity, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                } else {
                    util.toastMessage(activity, "Check your Network");
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {

            // If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Displaying a toast
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

            } else {
                util.toastMessage(activity, "Oops you just denied the permission");
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            displayTree.setImageBitmap(bitmap);

        }

    }

}
