package com.example.iduma.tree_tracking.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.iduma.tree_tracking.Model.PlantingModel;
import com.example.iduma.tree_tracking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Statistics extends AppCompatActivity {

    private Button btnRetrieve;
    private TextView tvAfforest;
    private DatabaseReference treeRef;
    private String nooftree;
    private int treeno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        tvAfforest = findViewById(R.id.tvAfforest);
        btnRetrieve = findViewById(R.id.btnAfforst);
        treeRef= FirebaseDatabase.getInstance().getReference().child("Afforestation");

        treeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                treeno=0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PlantingModel model = ds.getValue(PlantingModel.class);
                    nooftree =model.getNoOfTrees();
                    treeno=treeno+Integer.parseInt(nooftree);
                    Log.d("TAG",""+treeno);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAfforest.setText(""+treeno);
            }
        });


    }
}
