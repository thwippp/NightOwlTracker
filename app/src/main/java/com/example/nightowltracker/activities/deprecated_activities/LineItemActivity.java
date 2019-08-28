package com.example.nightowltracker.activities.deprecated_activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;

import java.util.ArrayList;

public class LineItemActivity extends AppCompatActivity {

    private static final String TAG = "LineItemActivity";
    private ArrayList<String> mNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_item);


        Log.d(TAG, "onCreate: started.");
        mNames.add("LineItemEntity 1");
        mNames.add("LineItemEntity 2");
        mNames.add("LineItemEntity 3");
        mNames.add("LineItemEntity 4");
        mNames.add("LineItemEntity 5");

        // Starts the RecyclerView
        initRecyclerView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames);
//        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
