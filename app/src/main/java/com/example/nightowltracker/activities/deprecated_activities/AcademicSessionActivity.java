package com.example.nightowltracker.activities.deprecated_activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.DBHelper;

import java.util.ArrayList;

public class AcademicSessionActivity extends AppCompatActivity {


    private static final String TAG = "AcademicSessionActivity";
    // Instance variable for SQLite DB Helper Object
    DBHelper myHelper;

    // vars
    private ArrayList<String> mNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_session);

        Log.d(TAG, "onCreate: started.");

        ArrayList<String> result;
        result = myHelper.getAS();

        mNames.add(result.get(0));

//        mNames.add("Term 1");
//        mNames.add("Term 2");

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
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(asData, this);
//        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
