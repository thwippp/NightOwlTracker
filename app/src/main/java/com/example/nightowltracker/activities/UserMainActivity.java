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
import com.example.nightowltracker.database.UserEntity;
import com.example.nightowltracker.ui.UserRecyclerViewAdapter;
import com.example.nightowltracker.view_model.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UserMainActivity extends AppCompatActivity {

    private static final String TAG = "UserMA";

    // vars
    private UserViewModel mViewModel;
    private List<UserEntity> uData = new ArrayList<>();
    private UserRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        mRecyclerView = findViewById(R.id.recycler_view);

        setTitle("Users");

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMainActivity.this, UserEditorActivity.class);
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

        final Observer<List<UserEntity>> userObserver =
                new Observer<List<UserEntity>>() {
                    @Override
                    public void onChanged(List<UserEntity> userEntities) {
                        uData.clear();
                        uData.addAll(userEntities);  // keeps a copy for the lifetime

                        if (mAdapter == null) {
                            mAdapter = new UserRecyclerViewAdapter(uData,
                                    UserMainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                };

        mViewModel = ViewModelProviders.of(this)
                .get(UserViewModel.class);
        mViewModel.mUser.observe(this, userObserver);  // subscribed to the data
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
        getMenuInflater().inflate(R.menu.menu_navigation_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Context context = UserMainActivity.this;
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
        }

        return super.onOptionsItemSelected(item);
    }

}
