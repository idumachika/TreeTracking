package com.example.iduma.tree_tracking.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iduma.tree_tracking.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Statistics extends AppCompatActivity {

    private Button btnRetrieve;
    private TextView tvAfforest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Parse.initialize(this);

        tvAfforest = findViewById(R.id.tvAfforest);
        btnRetrieve = findViewById(R.id.btnAfforst);


        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseQuery<ParseObject> query = ParseQuery.getQuery("Trees");
                query.selectKeys(Arrays.asList("treeNumber"));

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            List<String> results = new ArrayList<String>();
                            for (ParseObject parseObject : objects) {
                                results.add(parseObject.getString("treeNumber"));

                            }

                            Log.d("success", results.toArray().toString());

//                            Log.d("Success", "" + results.size());
                            Log.d("Success: ", results.toString());
//                            Log.d("Success.....", "" + query.whereContainsAll("treeNumber", results));


                        }

                        else {
                            Log.d("failed", "query error" + e.toString());
                        }
                    }
                });
            }
        });

    }
}
