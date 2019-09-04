package com.example.nightowltracker.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.ClassEntity;
import com.example.nightowltracker.utilities.Constants;
import com.example.nightowltracker.view_model.ClassViewModel;

import static com.example.nightowltracker.utilities.Constants.CLASS_CLASS_ID_KEY;

public class ClassEditorActivity extends AppCompatActivity {

    private static final String TAG = "CEditorActivity";

    private TextView mTitle;
    private TextView mClassCode;
    private TextView mStatus;
    private TextView mSessionId;

    private ClassViewModel mViewModel;
    private boolean mNewData;
    private boolean mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_editor);  // layout file
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check_white_36dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitle = findViewById(R.id.class_title);
        mClassCode = findViewById(R.id.class_code);
        mStatus = findViewById(R.id.class_status);
        mSessionId = findViewById(R.id.class_session_id);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }

        initViewModel();

    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(ClassViewModel.class);

        mViewModel.mLiveC.observe(this, new Observer<ClassEntity>() {
            @Override
            public void onChanged(ClassEntity classEntity) {
                if (classEntity != null && !mEditing) {

                    String ssid = String.valueOf(classEntity.getSessionId());

                    // Sets text on TextView
                    mTitle.setText(classEntity.getTitle());
                    mClassCode.setText(classEntity.getClassCode());
                    mStatus.setText(classEntity.getStatus());
                    mSessionId.setText(ssid);
                }
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            // brand new Data
            setTitle("New Class...");
            mNewData = true;
        } else {
            setTitle("Edit Class...");
            int classId = extras.getInt(CLASS_CLASS_ID_KEY);
            System.out.println("ClassId: " + classId);  //
            mViewModel.loadData(classId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewData) {
            // existing data. display new icon
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            mViewModel.deleteData(); // view model knows which "note" you're working with
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        // makes call to view model
        // gets text from textview in layout
        // Saves data to Db in given format

        // Casts sessionId as int
        int sid = Integer.parseInt(mSessionId.getText().toString());
        System.out.println("saving int to Db: " + sid);
        // Saves data to Db in the correct format
        mViewModel.saveData(
                mTitle.getText().toString(),
                mClassCode.getText().toString(),
                mStatus.getText().toString(),
                sid);
        finish();
    }

    // Live Data destroys data when the orientation changes.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
