package com.example.nightowltracker.activities;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.ClassEntity;
import com.example.nightowltracker.utilities.NotificationPublisher;
import com.example.nightowltracker.view_model.ClassViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.nightowltracker.utilities.Constants.CLASS_CHANNEL_ID;
import static com.example.nightowltracker.utilities.Constants.CLASS_ID_KEY;
import static com.example.nightowltracker.utilities.Constants.EDITING_KEY;

public class ClassEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "CEditorActivity";

    private TextView mTitle;
    private TextView mClassCode;
    public static List<String> uUserText = new ArrayList<>();
    private TextView mStartDate;
    private Switch mStartDateSwitch;
    private static List<String> mStatusItems = new ArrayList<>();
    private Spinner mStatus;
    private Spinner termSpinner;
    private Spinner userSpinner;
    private TextView mEndDate;
    private Switch mEndDateSwitch;

    private int asSessionIdItem;
    private String asTitleItem;
    public static List<Integer> asSessionId = new ArrayList<>();
    public static List<String> asTitle = new ArrayList<>();

    private int uUserIdItem;
    private String uUsernameItem;
    public static List<Integer> uUserId = new ArrayList<>();
    public static List<String> uUsername = new ArrayList<>();
    private TextView userTextArea;

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
        mStartDate = findViewById(R.id.class_start_date);
        mStartDateSwitch = findViewById(R.id.switch_class_start_date);
        mEndDate = findViewById(R.id.class_end_date);
        mEndDateSwitch = findViewById(R.id.switch_class_end_date);

        mStartDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // Toast.makeText(LineItemEditorActivity.this, "Checked.", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onCheckedChanged...");
                if (mStartDate.getText() != null) {
                    Log.i(TAG, "datenotnull");

                    // Sets format for dates
                    SimpleDateFormat sStartDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
                    Log.i(TAG, "sStartDate");

                    // Initializes dates with new date
                    Date startDate = new Date();
                    Log.i(TAG, "new startDate");

                    // Parses dates that user typed in according to format
                    try {
                        startDate = sStartDate.parse(mStartDate.getText().toString());

                        Log.i(TAG, "startDate parsed: " + startDate.toString());

                        Log.i(TAG, "startDate in Millis: " + startDate.getTime());

                        Calendar rightNow = Calendar.getInstance();
                        long millis = rightNow.getTimeInMillis();
                        Log.i(TAG, "millis: " + millis);

                        int diff = (int) (startDate.getTime() - millis);
                        Log.i(TAG, "diff: " + diff);

                        if (diff > 0) {
                            Log.i(TAG, "startDate: " + startDate.toString());
                            scheduleNotification(getNotification(mTitle.getText().toString(), startDate.toString()), diff);

                            Toast.makeText(ClassEditorActivity.this, "Your notification has been scheduled for " + startDate.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        Log.i(TAG, "error");
                        e.printStackTrace();
                    }

                }
            }
        });

        mEndDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // Toast.makeText(LineItemEditorActivity.this, "Checked.", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onCheckedChanged...");
                if (mEndDate.getText() != null) {
                    Log.i(TAG, "datenotnull");

                    // Sets format for dates
                    SimpleDateFormat sEndDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
                    Log.i(TAG, "sEndDate");

                    // Initializes dates with new date
                    Date endDate = new Date();
                    Log.i(TAG, "new dueDate");

                    // Parses dates that user typed in according to format
                    try {
                        endDate = sEndDate.parse(mEndDate.getText().toString());

                        Log.i(TAG, "endDate parsed: " + endDate.toString());

                        Log.i(TAG, "enddate in Millis: " + endDate.getTime());

                        Calendar rightNow = Calendar.getInstance();
                        long millis = rightNow.getTimeInMillis();
                        Log.i(TAG, "millis: " + millis);

                        int diff = (int) (endDate.getTime() - millis);
                        Log.i(TAG, "diff: " + diff);

                        if (diff > 0) {
                            Log.i(TAG, "endDate: " + endDate.toString());
                            scheduleNotification(getNotification(mTitle.getText().toString(), endDate.toString()), diff);

                            Toast.makeText(ClassEditorActivity.this, "Your notification has been scheduled for " + endDate.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        Log.i(TAG, "error");
                        e.printStackTrace();
                    }

                }
            }
        });

        // termSpinner element
        mStatus = findViewById(R.id.class_status);
        termSpinner = findViewById(R.id.class_session_id);
        userSpinner = findViewById(R.id.class_user_id);
        userTextArea = findViewById(R.id.class_user_text_area);

        // Add items to mStatus if it doesn't exist... seems like a waste to add again and again
        if (!mStatusItems.contains("--")) {
            mStatusItems.add("--");
        }
        if (!mStatusItems.contains("In Progress")) {
            mStatusItems.add("In Progress");
        }
        if (!mStatusItems.contains("Plan to Take")) {
            mStatusItems.add("Plan to Take");
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
                // Toast.makeText(mStatus.getContext(), "Item: " + item, Toast.LENGTH_LONG).show();
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

                // TODO HERE

                // Showing selected termSpinner item
                // Toast.makeText(termSpinner.getContext(), "Title: " + asTitleItem + ".\nSessionId: " + asSessionIdItem, Toast.LENGTH_LONG).show();
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

                userTextArea.setText(uUserText.get(uUserId.indexOf(uUserIdItem)));

                // Showing selected termSpinner item
                // Toast.makeText(userSpinner.getContext(), "Username: " + uUsernameItem + ".\nUserId: " + uUserIdItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> userAdapterView) {
// TODO Auto-generated method stub
            }
        });

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
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

        createNotificationChannel();

        initViewModel();
    }

    private void createNotificationChannel() {
        //System.out.println("Creating Notification Channel.");

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CLASS_CHANNEL_ID, CLASS_CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Class Notifications");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //Notification
    private void scheduleNotification(Notification notification, int delay) {
        //System.out.println("Scheduling Notification.");
        Log.i(TAG, "scheduleNotification, delay");

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;

        Log.i(TAG, "scheduleNotification: futureInMillis" + futureInMillis);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private Notification getNotification(String title, String content) {
        //System.out.println("Getting Notification.");
        Log.i(TAG, "getNotification");

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.check);
        builder.setChannelId(CLASS_CHANNEL_ID);
        return builder.build();
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

                    // Sets format for date
                    SimpleDateFormat sfsDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
                    SimpleDateFormat sfeDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);

                    // Sets format for date from Db
                    String sStartDate = sfsDate.format(classEntity.getStartDate());
                    String sEndDate = sfeDate.format(classEntity.getEndDate());

                    // Sets text on TextView, formatted correctly
                    mStartDate.setText(sStartDate);
                    mEndDate.setText(sEndDate);

//                    System.out.println("asSessionId.indexOf(classEntity.getSessionId(): " + asSessionId.indexOf(classEntity.getSessionId()));
//                    System.out.println("classEntity.getSessionId(): " + classEntity.getSessionId());
//                    System.out.println("asSessionIdItem: " + asSessionIdItem);
//                    System.out.println("asTitleItem: " + asTitleItem);
//                    System.out.println("asSessionId going into termSpinner: " + asSessionIdItem);

                    // gets the position in the list of the value from the Db
                    mStatus.setSelection(mStatusItems.indexOf(classEntity.getStatus()));
                    termSpinner.setSelection(asSessionId.indexOf(classEntity.getSessionId()));
                    userSpinner.setSelection(uUserId.indexOf(classEntity.getUserId()));

                    try {
                        userTextArea.setText(uUserText.get(uUserId.indexOf(classEntity.getUserId())));
                    } catch (Exception e) {
                        System.out.println(uUserText.toString());
                        e.printStackTrace();
                    }

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

        // Sets format for dates
        SimpleDateFormat sfsDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        SimpleDateFormat sfeDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);

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

        // Saves data to Db in the correct format
        mViewModel.saveData(
                mTitle.getText().toString(),
                mClassCode.getText().toString(),
                startDate,
                endDate,
                mStatus.getSelectedItem().toString(),
                asSessionIdItem,
                uUserIdItem);
        finish();

        //System.out.println("saving int to Db: " + asSessionIdItem);
    }

    // Live Data destroys data when the orientation changes.

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
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
