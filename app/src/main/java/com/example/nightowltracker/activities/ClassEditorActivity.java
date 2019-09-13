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
    private TextView mStatus;
//    private TextView mSessionId;

    //    public MutableLiveData<List<AcademicSessionEntity>> mSpinnerData;
    public static List<Integer> asSessionId = new ArrayList<>();
    public static List<String> asTitle = new ArrayList<>();
    private Spinner spinner;
    private int asSessionIdItem;
    private String asTitleItem;
//    // AS
//    private AcademicSessionViewModel getAsViewModel;
//    private List asDataList;
//    private List<AcademicSessionEntity> asData = new ArrayList<>();
//    private LiveData<List<AcademicSessionEntity>> liveAsData;
//    private RecyclerView asRecyclerView;
//    // AS
//    private AcademicSessionRecyclerViewAdapter asAdapter;
//
//    private AcademicSessionViewModel asViewModel;

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

        // Adds AS view model
//        asViewModel = ViewModelProviders.of(this).get(AcademicSessionViewModel.class);

        mTitle = findViewById(R.id.class_title);
        mClassCode = findViewById(R.id.class_code);
        mStatus = findViewById(R.id.class_status);
//        mSessionId = findViewById(R.id.class_session_id);

        // Spinner element
        spinner = findViewById(R.id.class_session_id);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, asTitle);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        initViewModel();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

        asTitleItem = parent.getItemAtPosition(position).toString();
        spinner.setSelection(asTitle.indexOf(asTitleItem));
        asSessionIdItem = asSessionId.get(position);

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Title: " + asTitleItem + ".\nSessionId: " + asSessionIdItem, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
// MOVED TO CLASSMAINACTIVTY
//    public void observeAS(){
//        asViewModel.mAcademicSession.observe(this, new Observer<List<AcademicSessionEntity>>() {
//            @Override
//            public void onChanged(List<AcademicSessionEntity> academicSessionEntities) {
//                // When a change happens to the ASE, if it isn't in a list, add it
//                for(AcademicSessionEntity as : academicSessionEntities) {
//                    if(!asSessionId.contains(as.getSessionId())) {
//                        asSessionId.add(as.getSessionId());
//                        asTitle.add(as.getTitle());
//
//                        System.out.println("Adding value to asSessionId: " + as.getSessionId());
//                        System.out.println("Adding value to asTitle: " + as.getTitle());
//                    }
//                }
//            }
//        });
//    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(ClassViewModel.class);

        mViewModel.mLiveC.observe(this, new Observer<ClassEntity>() {
            @Override
            public void onChanged(ClassEntity classEntity) {
                if (classEntity != null && !mEditing) {

//                    String ssid = String.valueOf(classEntity.getSessionId());

                    // Sets text on TextView
                    mTitle.setText(classEntity.getTitle());
                    mClassCode.setText(classEntity.getClassCode());
                    mStatus.setText(classEntity.getStatus());
//                    mSessionId.setText(ssid);

                    System.out.println("asSessionId.indexOf(classEntity.getSessionId(): " + asSessionId.indexOf(classEntity.getSessionId()));
                    System.out.println("classEntity.getSessionId(): " + classEntity.getSessionId());
                    System.out.println("asSessionIdItem: " + asSessionIdItem);
                    System.out.println("asTitleItem: " + asTitleItem);
                    System.out.println("asSessionId going into spinner: " + asSessionIdItem);

                    // gets the position in the list of the value from the Db
                    spinner.setSelection(asSessionId.indexOf(classEntity.getSessionId()));
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

        // Casts sessionId as int
//        int sid = Integer.parseInt(mSessionId.getText().toString());
        System.out.println("saving int to Db: " + asSessionIdItem);
        // Saves data to Db in the correct format
        mViewModel.saveData(
                mTitle.getText().toString(),
                mClassCode.getText().toString(),
                mStatus.getText().toString(),
                asSessionIdItem);  // TODO MAY NEED TO CHECK THIS.  SAVING THE RIGHT VALUE TO DB?
        finish();
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
}
