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
import com.example.nightowltracker.database.LineItemEntity;
import com.example.nightowltracker.utilities.Constants;
import com.example.nightowltracker.view_model.LineItemViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.nightowltracker.utilities.Constants.LINE_ITEM_ID;

public class LineItemEditorActivity extends AppCompatActivity {

    private static final String TAG = "LIEditorActivity";

    private TextView mTitle;
    private TextView mDescription;
    private TextView mCategory;
    private TextView mAssignDate;
    private TextView mDueDate;
    private TextView mClassId;

    private LineItemViewModel mViewModel;
    private boolean mNewData;
    private boolean mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_item_editor);  // layout file
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check_white_36dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitle = findViewById(R.id.line_item_title);
        mDescription = findViewById(R.id.line_item_description);
        mCategory = findViewById(R.id.line_item_category);
        mAssignDate = findViewById(R.id.line_item_assign_date);
        mDueDate = findViewById(R.id.line_item_due_date);
        mClassId = findViewById(R.id.line_item_class_id);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }

        initViewModel();

    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(LineItemViewModel.class);

        mViewModel.mLiveLI.observe(this, new Observer<LineItemEntity>() {
            @Override
            public void onChanged(LineItemEntity lineItemEntity) {
                if (lineItemEntity != null && !mEditing) {


                    // Sets format for date
                    SimpleDateFormat sfaDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    SimpleDateFormat sfdDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

                    // Sets format for date from Db
                    String sAssignDate = sfaDate.format(lineItemEntity.getAssignDate());
                    String sDueDate = sfdDate.format(lineItemEntity.getDueDate());

                    // Casts int to String
                    String scid = String.valueOf(lineItemEntity.getClassId());

                    // Sets text on TextView
                    mTitle.setText(lineItemEntity.getTitle());
                    mDescription.setText(lineItemEntity.getDescription());
                    mCategory.setText(lineItemEntity.getCategory());
                    mAssignDate.setText(sAssignDate);
                    mDueDate.setText(sDueDate);
                    mClassId.setText(scid);
                }
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            // brand new Data
            setTitle("New Line Item...");
            mNewData = true;
        } else {
            setTitle("Edit Line Item...");
            int lineItemId = extras.getInt(LINE_ITEM_ID);
            System.out.println("LineItemId: " + lineItemId);  //
            mViewModel.loadData(lineItemId);
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

        // Sets format for dates
        SimpleDateFormat sfaDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        SimpleDateFormat sfdDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        // Initializes dates with new date
        Date assignDate = new Date();
        Date dueDate = new Date();

        // Parses dates that user typed in according to format
        try {
            assignDate = sfaDate.parse(mAssignDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            dueDate = sfdDate.parse(mDueDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Casts classId as int
        int cid = Integer.parseInt(mClassId.getText().toString());

        // Saves data to Db in the correct format
        mViewModel.saveData(mTitle.getText().toString(),
                mDescription.getText().toString(),
                mCategory.getText().toString(),
                assignDate,
                dueDate,
                cid);
        finish();
    }

    // Live Data destroys data when the orientation changes.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
