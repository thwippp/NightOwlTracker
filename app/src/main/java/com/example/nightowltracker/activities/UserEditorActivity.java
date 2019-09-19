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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.UserEntity;
import com.example.nightowltracker.utilities.Constants;
import com.example.nightowltracker.view_model.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.nightowltracker.utilities.Constants.USER_ID;

public class UserEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "UEditorActivity";
    private static List<String> mRoleItems = new ArrayList<>();
    private TextView mUsername;
    private TextView mGivenname;
    private TextView mFamilyname;
    private Spinner mRole;

    private TextView mEmail;
    private TextView mPhone;
    private TextView mSms;

    private UserViewModel mViewModel;
    private boolean mNewData;
    private boolean mEditing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editor);  // layout file
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check_white_36dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mUsername = findViewById(R.id.user_username);
        mGivenname = findViewById(R.id.user_givenname);
        mFamilyname = findViewById(R.id.user_familyname);

        // termSpinner element
        mRole = findViewById(R.id.user_role);

        mEmail = findViewById(R.id.user_email);
        mPhone = findViewById(R.id.user_phone);
        mSms = findViewById(R.id.user_sms);

        // Add items to mRole if it doesn't exist... seems like a waste to add again and again
        if (!mRoleItems.contains("Course Instructor")) {
            mRoleItems.add("Course Instructor");
        }
        if (!mRoleItems.contains("Student")) {
            mRoleItems.add("Student");
        }

        // spinner setOnITemSelectedListeners
        mRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a termSpinner item
                String item = mRole.getItemAtPosition(i).toString();

                // Showing selected termSpinner item
                // Toast.makeText(mRole.getContext(), "Item: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
// TODO Auto-generated method stub
            }
        });

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }

        // Creating adapter for spinners
        ArrayAdapter<String> mRoleDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mRoleItems);

        // Drop down layout style - list view with radio button
        mRoleDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinners
        mRole.setAdapter(mRoleDataAdapter);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(UserViewModel.class);

        mViewModel.mLiveU.observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity userEntity) {
                if (userEntity != null && !mEditing) {

                    // Sets text on TextView
                    mUsername.setText(userEntity.getUsername());
                    mGivenname.setText(userEntity.getGivenName());
                    mFamilyname.setText(userEntity.getFamilyName());

                    // gets the position in the list of the value from the Db
                    mRole.setSelection(mRoleItems.indexOf(userEntity.getRole()));

                    mEmail.setText(userEntity.getEmail());
                    mPhone.setText(userEntity.getPhone());
                    mSms.setText(userEntity.getSms());

                }
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            // brand new Data
            setTitle("New User...");
            mNewData = true;
        } else {
            setTitle("Edit User...");
            int userId = extras.getInt(USER_ID);
            mViewModel.loadData(userId);
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
                mUsername.getText().toString(),
                mGivenname.getText().toString(),
                mFamilyname.getText().toString(),
                mRole.getSelectedItem().toString(),
                mEmail.getText().toString(),
                mPhone.getText().toString(),
                mSms.getText().toString());
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Auto-generated
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // Auto-generated
    }
}
