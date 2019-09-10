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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.LineItemEntity;
import com.example.nightowltracker.ui.LineItemRecyclerViewAdapter;
import com.example.nightowltracker.view_model.LineItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LineItemMainActivity extends AppCompatActivity {

    private static final String TAG = "LineItemMA";

    // vars
    private LineItemViewModel mViewModel;
    private List<LineItemEntity> lData = new ArrayList<>();
    private LineItemRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_main);
        mRecyclerView = findViewById(R.id.recycler_view);  //TODO adapter per class?

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LineItemMainActivity.this, LineItemEditorActivity.class);
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

        final Observer<List<LineItemEntity>> lineItemObserver =
                new Observer<List<LineItemEntity>>() {
                    @Override
                    public void onChanged(List<LineItemEntity> lineItemEntities) {
                        lData.clear();
                        lData.addAll(lineItemEntities);  // keeps a copy for the lifetime

                        if (mAdapter == null) {
                            mAdapter = new LineItemRecyclerViewAdapter(lData,
                                    LineItemMainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                };

        mViewModel = ViewModelProviders.of(this)
                .get(LineItemViewModel.class);
        mViewModel.mLineItem.observe(this, lineItemObserver);  // subscribed to the data
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        mRecyclerView = findViewById(R.id.recycler_view);  // todo
        mRecyclerView.setHasFixedSize(true);  // Each item is the same height. Avoids re-measurements.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Adds some decoration between items
        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_academic_session_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_go_to_main_activity) {
            Intent intent = new Intent(LineItemMainActivity.this, MainActivity.class);
            Context context = this;
            context.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}