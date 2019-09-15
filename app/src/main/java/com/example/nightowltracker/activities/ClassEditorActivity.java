package com.example.nightowltracker.activities;

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
import com.example.nightowltracker.database.ClassEntity;
import com.example.nightowltracker.utilities.Constants;
import com.example.nightowltracker.view_model.ClassViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.nightowltracker.utilities.Constants.CLASS_ID_KEY;

public class ClassEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "CEditorActivity";

    private TextView mTitle;
    private TextView mClassCode;
    private static List<String> mStatusItems = new ArrayList<>();
    private Spinner mStatus;
    private Spinner termSpinner;
    private Spinner userSpinner;

    private int asSessionIdItem;
    private String asTitleItem;
    public static List<Integer> asSessionId = new ArrayList<>();
    public static List<String> asTitle = new ArrayList<>();

    private int uUserIdItem;
    private String uUsernameItem;
    public static List<Integer> uUserId = new ArrayList<>();
    public static List<String> uUsername = new ArrayList<>();

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

        // termSpinner element
        mStatus = findViewById(R.id.class_status);
        termSpinner = findViewById(R.id.class_session_id);
        userSpinner = findViewById(R.id.class_user_id);

        // Add items to mStatus if it doesn't exist... seems like a waste to add again and again
        if (!mStatusItems.contains("--")) {
            mStatusItems.add("--");
        }
        if (!mStatusItems.contains("Plan to Take")) {
            mStatusItems.add("Plan to Take");
        }
        if (!mStatusItems.contains("In Progress")) {
            mStatusItems.add("In Progress");
        }
        if (!mStatusItems.contains("Completed")) {
            mStatusItems.add("Completed");
        }
        if (!mStatusItems.contains("Dropped")) {
            mStatusItems.add("Dropped");
        }

        // spinner setOnITemSelectedListeners
        mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a termSpinner item
                String item = mStatus.getItemAtPosition(i).toString();

                // Showing selected termSpinner item
                Toast.makeText(mStatus.getContext(), "Item: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
// TODO Auto-generated method stub
            }
        });
        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a termSpinner item

                asTitleItem = termSpinner.getItemAtPosition(i).toString();
                termSpinner.setSelection(asTitle.indexOf(asTitleItem));
                asSessionIdItem = asSessionId.get(i);

                // Showing selected termSpinner item
                Toast.makeText(termSpinner.getContext(), "Title: " + asTitleItem + ".\nSessionId: " + asSessionIdItem, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
// TODO Auto-generated method stub
            }
        });
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> userAdapterView, View view, int i, long l) {
                // On selecting a termSpinner item

                uUsernameItem = userSpinner.getItemAtPosition(i).toString();
                userSpinner.setSelection(uUsername.indexOf(uUsernameItem));
                uUserIdItem = uUserId.get(i);

                // Showing selected termSpinner item
                Toast.makeText(userSpinner.getContext(), "Username: " + uUsernameItem + ".\nUserId: " + uUserIdItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> userAdapterView) {
// TODO Auto-generated method stub
            }
        });

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }

        // Creating adapter for spinners
        ArrayAdapter<String> mStatusDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mStatusItems);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, asTitle);
        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, uUsername);

        // Drop down layout style - list view with radio button
        mStatusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinners
        mStatus.setAdapter(mStatusDataAdapter);
        termSpinner.setAdapter(dataAdapter);
        userSpinner.setAdapter(userAdapter);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(ClassViewModel.class);

        mViewModel.mLiveC.observe(this, new Observer<ClassEntity>() {
            @Override
            public void onChanged(ClassEntity classEntity) {
                if (classEntity != null && !mEditing) {

                    // Sets text on TextView
                    mTitle.setText(classEntity.getTitle());
                    mClassCode.setText(classEntity.getClassCode());

                    System.out.println("asSessionId.indexOf(classEntity.getSessionId(): " + asSessionId.indexOf(classEntity.getSessionId()));
                    System.out.println("classEntity.getSessionId(): " + classEntity.getSessionId());
                    System.out.println("asSessionIdItem: " + asSessionIdItem);
                    System.out.println("asTitleItem: " + asTitleItem);
                    System.out.println("asSessionId going into termSpinner: " + asSessionIdItem);

                    // gets the position in the list of the value from the Db
                    mStatus.setSelection(mStatusItems.indexOf(classEntity.getStatus()));
                    termSpinner.setSelection(asSessionId.indexOf(classEntity.getSessionId()));
                    userSpinner.setSelection(uUserId.indexOf(classEntity.getUserId()));
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
            int classId = extras.getInt(CLASS_ID_KEY);
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

        // Saves data to Db in the correct format
        mViewModel.saveData(
                mTitle.getText().toString(),
                mClassCode.getText().toString(),
                mStatus.getSelectedItem().toString(),
                asSessionIdItem,
                uUserIdItem);
        finish();

        System.out.println("saving int to Db: " + asSessionIdItem);
    }

    // Live Data destroys data when the orientation changes.

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Auto-generated
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // Auto-generated
    }
}
