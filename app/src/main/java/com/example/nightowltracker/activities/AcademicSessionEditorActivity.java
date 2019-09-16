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
import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.utilities.Constants;
import com.example.nightowltracker.view_model.AcademicSessionViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.nightowltracker.utilities.Constants.ACADEMIC_SESSION_ID_KEY;

public class AcademicSessionEditorActivity extends AppCompatActivity {

    private static final String TAG = "ASEditorActivity";

    private TextView mTitle;
    private TextView mStartDate;
    private TextView mEndDate;

    private AcademicSessionViewModel mViewModel;
    private boolean mNewData;
    private boolean mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_session_editor);  // layout file
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check_white_36dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitle = findViewById(R.id.as_title);
        mStartDate = findViewById(R.id.as_start_date);
        mEndDate = findViewById(R.id.as_end_date);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }

        initViewModel();

    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(AcademicSessionViewModel.class);

        mViewModel.mLiveAs.observe(this, new Observer<AcademicSessionEntity>() {
            @Override
            public void onChanged(AcademicSessionEntity academicSessionEntity) {
                if (academicSessionEntity != null && !mEditing) {

                    // Sets format for date
                    SimpleDateFormat sfsDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    SimpleDateFormat sfeDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

                    // Sets format for date from Db
                    String sStartDate = sfsDate.format(academicSessionEntity.getStartDate());
                    String sEndDate = sfeDate.format(academicSessionEntity.getEndDate());

                    // Sets text on TextView, formatted correctly
                    mTitle.setText(academicSessionEntity.getTitle());
                    mStartDate.setText(sStartDate);
                    mEndDate.setText(sEndDate);
                }
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            // brand new Data
            setTitle("New Term...");
            mNewData = true;
        } else {
            setTitle("Edit Term...");
            int sessionId = extras.getInt(ACADEMIC_SESSION_ID_KEY);
            mViewModel.loadData(sessionId);
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
            mViewModel.deleteData(); // view model knows which "note" you're working with'
            // TODO Some sort of toast message to the user
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

        // Sets format for dates
        SimpleDateFormat sfsDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        SimpleDateFormat sfeDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        // Initializes dates with new date
        Date startDate = new Date();
        Date endDate = new Date();

        // Parses dates that user typed in according to format
        try {
            startDate = sfsDate.parse(mStartDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            endDate = sfeDate.parse(mEndDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Saves data to Db in given format
        mViewModel.saveData(mTitle.getText().toString(), startDate, endDate);

        finish();
    }

    // Live Data destroys data when the orientation changes.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


}
