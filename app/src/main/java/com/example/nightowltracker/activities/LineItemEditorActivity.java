package com.example.nightowltracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.nightowltracker.utilities.Constants.LINE_ITEM_ID;

public class LineItemEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "LIEditorActivity";

    private TextView mTitle;
    private TextView mDescription;
    public static List<Integer> cClassId = new ArrayList<>();
    private TextView mAssignDate;
    private TextView mDueDate;
    public static List<String> cTitle = new ArrayList<>();
    private static List<String> mCategoryItems = new ArrayList<>();
    private Spinner mCategory;
    private Spinner mClassId;
    private int cClassIdItem;
    private String cTitleItem;

    private LineItemViewModel mViewModel;
    private boolean mNewData;
    private boolean mEditing;

    private Executor executor = Executors.newSingleThreadExecutor();


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
        // Spinner element
        mClassId = findViewById(R.id.line_item_class_id);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }

        // Add items to mStatus if it doesn't exist... seems like a waste to add again and again
        if (!mCategoryItems.contains("Objective Assessment")) {
            mCategoryItems.add("Objective Assessment");
        }
        if (!mCategoryItems.contains("Performance Assessment")) {
            mCategoryItems.add("Performance Assessment");
        }
        if (!mCategoryItems.contains("Note")) {
            mCategoryItems.add("Note");
        }


        // Spinner click listener
        mCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a termSpinner item
                String item = mCategory.getItemAtPosition(i).toString();

                // Showing selected termSpinner item
                Toast.makeText(mCategory.getContext(), "Item: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
// TODO Auto-generated method stub
            }
        });
        mClassId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cTitleItem = mClassId.getItemAtPosition(i).toString();
                mClassId.setSelection(cTitle.indexOf(cTitleItem));
                cClassIdItem = cClassId.get(i);
                // Showing selected termSpinner item
                Toast.makeText(mClassId.getContext(), "Title: " + cTitleItem + ".\nClassId: " + cClassIdItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
// TODO Auto-generated method stub
            }
        });


        // Creating adapter for spinners
        ArrayAdapter<String> cCategoryDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mCategoryItems);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cTitle);

        // Drop down layout style - list view with radio button
        cCategoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinners
        mCategory.setAdapter(cCategoryDataAdapter);
        mClassId.setAdapter(dataAdapter);

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
//                    String scid = String.valueOf(lineItemEntity.getClassId());

                    // Sets text on TextView
                    mTitle.setText(lineItemEntity.getTitle());
                    mDescription.setText(lineItemEntity.getDescription());
//                    mCategory.setText(lineItemEntity.getCategory());
                    mCategory.setSelection(mCategoryItems.indexOf(lineItemEntity.getCategory()));
                    mAssignDate.setText(sAssignDate);
                    mDueDate.setText(sDueDate);
//                    mClassId.setText(scid);
                    mClassId.setSelection(cClassId.indexOf(lineItemEntity.getClassId()));

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
        } else if (item.getItemId() == R.id.action_share_line_item) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);

            String shareText = "Title: " + mTitle.getText() + "\nDescription: " + mDescription.getText() + "\nCategory: " + mCategory.getSelectedItem().toString() + "\nAssign Date: " + mAssignDate.getText() + "\nDueDate: " + mDueDate.getText();

            sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
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
//        int cid = Integer.parseInt(mClassId.getText().toString());

        // Saves data to Db in the correct format
        mViewModel.saveData(mTitle.getText().toString(),
                mDescription.getText().toString(),
                mCategory.getSelectedItem().toString(),
                assignDate,
                dueDate,
                cClassIdItem);
        finish();
    }

    // Live Data destroys data when the orientation changes.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Nothing
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Nothing
    }
}
