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
import com.example.nightowltracker.database.LineItemEntity;
import com.example.nightowltracker.utilities.Constants;
import com.example.nightowltracker.utilities.NotificationPublisher;
import com.example.nightowltracker.view_model.LineItemViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.nightowltracker.utilities.Constants.ASSESSMENT_CHANNEL_ID;
import static com.example.nightowltracker.utilities.Constants.LINE_ITEM_ID;

public class LineItemEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "LIEditorActivity";

    private TextView mTitle;
    private TextView mDescription;
    public static List<Integer> cClassId = new ArrayList<>();
    private TextView mAssignDate;
    private TextView mDueDate;
    private Switch mDueDateSwitch;
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

//    private dueDateAlarm;


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
        mDueDateSwitch = findViewById(R.id.switch_line_item_due_date);


        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }

        // Add items to mStatus if it doesn't exist... seems like a waste to add again and again
        if (!mCategoryItems.contains("Note")) {
            mCategoryItems.add("Note");
        }
        if (!mCategoryItems.contains("Objective Assessment")) {
            mCategoryItems.add("Objective Assessment");
        }
        if (!mCategoryItems.contains("Performance Assessment")) {
            mCategoryItems.add("Performance Assessment");
        }


        // Spinner click listener
        mCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a termSpinner item
                String item = mCategory.getItemAtPosition(i).toString();

                // Showing selected termSpinner item
                // Toast.makeText(mCategory.getContext(), "Item: " + item, Toast.LENGTH_LONG).show();
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
                // Toast.makeText(mClassId.getContext(), "Title: " + cTitleItem + ".\nClassId: " + cClassIdItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
// TODO Auto-generated method stub
            }
        });
        mDueDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // Toast.makeText(LineItemEditorActivity.this, "Checked.", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onCheckedChanged...");

                if (mDueDate.getText() != null) {
                    Log.i(TAG, "datenotnull");

                    // Sets format for dates
                    SimpleDateFormat sdueDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
                    Log.i(TAG, "sDueDate");

                    // Initializes dates with new date
                    Date dueDate = new Date();
                    Log.i(TAG, "new dueDate");

                    // Parses dates that user typed in according to format
                    try {
                        dueDate = sdueDate.parse(mDueDate.getText().toString());

                        Log.i(TAG, "dueDate parsed: " + dueDate.toString());

                        Log.i(TAG, "duedate in Millis: " + dueDate.getTime());

                        Calendar rightNow = Calendar.getInstance();
                        long millis = rightNow.getTimeInMillis();
                        Log.i(TAG, "millis: " + millis);

                        int diff = (int) (dueDate.getTime() - millis);
                        Log.i(TAG, "diff: " + diff);

                        Log.i(TAG, "dueDate: " + dueDate.toString());
                        scheduleNotification(getNotification(mTitle.getText().toString(), mDescription.getText().toString() + "\n" + dueDate.toString()), diff);

                        Toast.makeText(LineItemEditorActivity.this, "Your notification has been scheduled for " + dueDate.toString(), Toast.LENGTH_SHORT).show();

                    } catch (ParseException e) {
                        Log.i(TAG, "error");
                        e.printStackTrace();
                    }

                }
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

        createNotificationChannel();

        initViewModel();

    }

    private void createNotificationChannel() {
        //System.out.println("Creating Notification Channel.");

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ASSESSMENT_CHANNEL_ID, ASSESSMENT_CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Assessment Notifications");
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
        builder.setChannelId(ASSESSMENT_CHANNEL_ID);
        return builder.build();
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
            //System.out.println("LineItemId: " + lineItemId);  //
            mViewModel.loadData(lineItemId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewData) {
            // existing data. display new icon
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_sharing, menu);
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
