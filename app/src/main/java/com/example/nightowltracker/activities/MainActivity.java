package com.example.nightowltracker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.ui.RecyclerViewAdapter;
import com.example.nightowltracker.view_model.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // vars
    private MainViewModel mViewModel;
    private ArrayList<String> mNames = new ArrayList<>();  // TODO DELETE ME
    private List<AcademicSessionEntity> asData = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;

    // Butterknife
    // @Bind(R.id.recycler_view);
    // RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView; // = findViewById(R.id.recycler_view);  // TODO figure out how to bind this without butterknife

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TEST
        mRecyclerView = findViewById(R.id.recycler_view);  // TODO Kinda works
        //TEST

        final FloatingActionButton fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Context context = fab.getContext();
                context.startActivity(intent);
            }
        });

        // Starts the RecyclerView
        initRecyclerView();

        //Init view model
        initViewModel();

    }

    private void initViewModel() {

        final Observer<List<AcademicSessionEntity>> academicSessionObserver =
                new Observer<List<AcademicSessionEntity>>() {
                    @Override
                    public void onChanged(List<AcademicSessionEntity> academicSessionEntities) {
                        asData.clear();
                        asData.addAll(academicSessionEntities);  // keeps a copy for the lifetime

                        if (mAdapter == null) {
                            mAdapter = new RecyclerViewAdapter(asData,
                                    MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                };

        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        mViewModel.mAcademicSession.observe(this, academicSessionObserver);  // subscribed to the data
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        mRecyclerView = findViewById(R.id.recycler_view);  // todo
        mRecyclerView.setHasFixedSize(true);  // Each item is the same height. Avoids re-measurements.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAllData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllData() {
        mViewModel.deleteAllData();
    }


    private void addSampleData() {
        mViewModel.addSampleData();
    }


}
