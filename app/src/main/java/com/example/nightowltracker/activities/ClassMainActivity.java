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
import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.database.ClassEntity;
import com.example.nightowltracker.database.UserEntity;
import com.example.nightowltracker.ui.ClassByTermRecyclerViewAdapter;
import com.example.nightowltracker.ui.ClassRecyclerViewAdapter;
import com.example.nightowltracker.view_model.AcademicSessionViewModel;
import com.example.nightowltracker.view_model.ClassViewModel;
import com.example.nightowltracker.view_model.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ClassMainActivity extends AppCompatActivity {

    private static final String TAG = "ClassMA";

    // vars
    private ClassViewModel mViewModel;
    private List<ClassEntity> cData = new ArrayList<>();
    private ClassRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private AcademicSessionViewModel asViewModel;
    private UserViewModel uViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_main);
        mRecyclerView = findViewById(R.id.recycler_view);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassMainActivity.this, ClassEditorActivity.class);
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
        uViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        // Populate lists in Editor
        observeAS();
        observeU();

    }

    private void initViewModel() {

        final Observer<List<ClassEntity>> classObserver =
                new Observer<List<ClassEntity>>() {
                    @Override
                    public void onChanged(List<ClassEntity> classEntities) {
                        cData.clear();
                        cData.addAll(classEntities);  // keeps a copy for the lifetime

                        if (mAdapter == null) {
                            mAdapter = new ClassRecyclerViewAdapter(cData,
                                    ClassMainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                };

        mViewModel = ViewModelProviders.of(this)
                .get(ClassViewModel.class);
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

    public void observeAS() {
        asViewModel.mAcademicSession.observe(this, new Observer<List<AcademicSessionEntity>>() {
            @Override
            public void onChanged(List<AcademicSessionEntity> academicSessionEntities) {
                // When a change happens to the ASE, if it isn't in a list, add it
                for (AcademicSessionEntity as : academicSessionEntities) {
                    if (!ClassEditorActivity.asSessionId.contains(as.getSessionId())) {
                        ClassEditorActivity.asSessionId.add(as.getSessionId());
                        ClassEditorActivity.asTitle.add(as.getTitle());

                        System.out.println("Adding value to asSessionId: " + as.getSessionId());
                        System.out.println("Adding value to asTitle: " + as.getTitle());
                    }
                    if (!ClassByTermRecyclerViewAdapter.asSessionId.contains(as.getSessionId())) {
                        ClassByTermRecyclerViewAdapter.asSessionId.add(as.getSessionId());
                        ClassByTermRecyclerViewAdapter.asTitle.add(as.getTitle());

                        System.out.println("Adding value to asSessionId: " + as.getSessionId());
                        System.out.println("Adding value to asTitle: " + as.getTitle());
                    }
                }
            }
        });
    }

    public void observeU() {
        uViewModel.mUser.observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                // When a change happens to the ASE, if it isn't in a list, add it
                for (UserEntity u : userEntities) {
                    if (!ClassEditorActivity.uUserId.contains(u.getUserId())) {
                        ClassEditorActivity.uUserId.add(u.getUserId());
                        ClassEditorActivity.uUsername.add(u.getUsername());
                        ClassEditorActivity.uUserText.add(u.toString());

                        System.out.println("Adding value to uUserId: " + u.getUserId());
                        System.out.println("Adding value to uUsername: " + u.getUsername());
                        System.out.println("Adding value to uUserText: " + u.toString());
                    }

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
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
        Context context = ClassMainActivity.this;
        Intent intent;

        //noinspection SimplifiableIfStatement
        switch (id) {
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
            case (R.id.action_go_to_class_by_term):
                intent = new Intent(context, ClassByTermActivity.class);
                context.startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
