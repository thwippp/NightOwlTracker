package com.example.nightowltracker.activities.deprecated_activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.ui.RecyclerViewAdapter;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";
    private ArrayList<String> mNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Log.d(TAG, "onCreate: started.");
        mNames.add("UserEntity 1");
        mNames.add("UserEntity 2");
        mNames.add("UserEntity 3");

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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
