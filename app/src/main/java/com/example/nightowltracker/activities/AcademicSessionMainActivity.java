package com.example.nightowltracker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.ui.AcademicSessionRecyclerViewAdapter;
import com.example.nightowltracker.view_model.AcademicSessionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AcademicSessionMainActivity extends AppCompatActivity {

    private static final String TAG = "AcademicSessionMA";

    // vars
    private AcademicSessionViewModel mViewModel;
    private List<AcademicSessionEntity> asData = new ArrayList<>();
    private AcademicSessionRecyclerViewAdapter mAdapter;

    public static Boolean isFKViolation = false;

    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_session_main);
        mRecyclerView = findViewById(R.id.recycler_view);


        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcademicSessionMainActivity.this, AcademicSessionEditorActivity.class);
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
                            mAdapter = new AcademicSessionRecyclerViewAdapter(asData,
                                    AcademicSessionMainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                };

        mViewModel = ViewModelProviders.of(this)
                .get(AcademicSessionViewModel.class);
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
        Context context = AcademicSessionMainActivity.this;
        Intent intent;

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_add_sample_data):
                addSampleData();
                break;
            case (R.id.action_delete_all):
                deleteAllData();
                break;
            case (R.id.action_go_to_main_activity):
                intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                break;
            case (R.id.action_go_to_academic_session_main_activity):
                intent = new Intent(context, AcademicSessionMainActivity.class);
                context.startActivity(intent);
                break;
            case (R.id.action_go_to_class_main_activity):
                intent = new Intent(context, ClassMainActivity.class);
                context.startActivity(intent);
                break;
            case (R.id.action_go_to_line_item_main_activity):
                intent = new Intent(context, LineItemMainActivity.class);
                context.startActivity(intent);
                break;
            case (R.id.action_go_to_user_main_activity):
                intent = new Intent(context, UserMainActivity.class);
                context.startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isFKViolation) {
            Toast.makeText(this, "Sorry. A term cannot be deleted if courses are assigned to it.", Toast.LENGTH_LONG).show();
            isFKViolation = false;
        }

    }

    private void deleteAllData() {
        mViewModel.deleteAllData();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }

}
