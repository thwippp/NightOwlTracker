package com.example.nightowltracker.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.model.DBHelper;
import com.example.nightowltracker.ui.RecyclerViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // vars
    private ArrayList<String> mNames = new ArrayList<>();

    // Instance variable for SQLite DB Helper Object
    DBHelper myHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started.");
        mNames.add("Terms");
        mNames.add("Classes");
        mNames.add("Users");

        // Starts the RecyclerView
        initRecyclerView();

        // Creates DB object
        myHelper = new DBHelper(MainActivity.this);
        myHelper.getWritableDatabase();  // creates or opens database
        Toast.makeText(MainActivity.this, myHelper.getDatabaseName() + " opened!", Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onPause(){
        super.onPause();

        myHelper.close();
        Toast.makeText(MainActivity.this, myHelper.getDatabaseName() + " closed!", Toast.LENGTH_SHORT).show();
    }


    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
