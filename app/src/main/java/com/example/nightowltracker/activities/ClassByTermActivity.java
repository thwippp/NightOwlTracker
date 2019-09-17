package com.example.nightowltracker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.database.ClassEntity;
import com.example.nightowltracker.ui.ClassByTermRecyclerViewAdapter;
import com.example.nightowltracker.view_model.AcademicSessionViewModel;
import com.example.nightowltracker.view_model.ClassByTermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ClassByTermActivity extends AppCompatActivity {

    private static final String TAG = "ClassBTA";

    // vars
    private ClassByTermViewModel mViewModel;
    private List<ClassEntity> cData = new ArrayList<>();
    private List<AcademicSessionEntity> asData = new ArrayList<>();

    private ClassByTermRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private AcademicSessionViewModel asViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_by_term);
        mRecyclerView = findViewById(R.id.recycler_view);

        final FloatingActionButton fab = findViewById(R.id.backFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassByTermActivity.this, ClassMainActivity.class);
                Context context = fab.getContext();
                context.startActivity(intent);
            }
        });

        // Starts the RecyclerView
        initRecyclerView();

        //Init view model
        initViewModel();

        // Adds AS view model
        asViewModel = ViewModelProviders.of(this).get(AcademicSessionViewModel.class);

    }

    private void initViewModel() {

        final Observer<List<ClassEntity>> classObserver =
                new Observer<List<ClassEntity>>() {
                    @Override
                    public void onChanged(List<ClassEntity> classEntities) {
                        cData.clear();
                        cData.addAll(classEntities);  // keeps a copy for the lifetime

                        if (mAdapter == null) {
                            mAdapter = new ClassByTermRecyclerViewAdapter(cData,
                                    ClassByTermActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                };

        mViewModel = ViewModelProviders.of(this)
                .get(ClassByTermViewModel.class);
        mViewModel.mClass.observe(this, classObserver);  // subscribed to the data
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

}