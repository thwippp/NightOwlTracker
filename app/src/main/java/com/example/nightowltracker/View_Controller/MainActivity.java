package com.example.nightowltracker.View_Controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.Model.DBHelper;
import com.example.nightowltracker.R;
import com.example.nightowltracker.UI.RecyclerViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    // Instance variable for SQLite DB Helper Object
    DBHelper myHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started.");
        mNames.add("Terms");
        mNames.add("Classes");
        mNames.add("Users");

//        for(AcademicSession session : SampleData.getAcademicSessions()){
//            String title = String.valueOf(session.getTitle());
//            mNames.add(title);
//            System.out.println(title);
//        }

        // Starts the RecyclerView
        initRecyclerView();
        initImageBitmaps();


        // Creates DB object
        myHelper = new DBHelper(MainActivity.this);
        myHelper.getWritableDatabase();  // creates or opens database
        Toast.makeText(MainActivity.this, myHelper.getDatabaseName() + " opened!", Toast.LENGTH_SHORT).show();

        // Some tests
//        String dropTableName = "AcademicSession";
//        myHelper.dropTable(dropTableName);
//        myHelper.createTable(tableName);
//        AcademicSession academicSession = new AcademicSession(1, "Term 1", Date.valueOf("2019-09-04"), Date.valueOf("2019-12-21"));
//        ArrayList<String> as = academicSession.createTableAcademicSession();
//
//        try {
//            myHelper.createTable(as.get(0), as.get(1));
//        }
//        catch(Exception e) {
//         System.out.println("Something broke.");
//        }

//        asData.addAll(SampleData.getAcademicSessions());
//        for(AcademicSession session : asData){
//            System.out.println(session.toString());
//        }
//
    }

    @Override
    protected void onPause(){
        super.onPause();

        myHelper.close();
        Toast.makeText(MainActivity.this, myHelper.getDatabaseName() + " closed!", Toast.LENGTH_SHORT).show();
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        String academicSessionsPath = "C:\\AndroidProjects\\NightOwlTracker\\app\\src\\main\\res\\drawable-xxxhdpi\\ic_calendar_range_grey600_24dp.png";
        String classesPath = "C:\\AndroidProjects\\NightOwlTracker\\app\\src\\main\\res\\drawable-xxxhdpi\\ic_google_classroom_grey600_24dp.png";
        String usersPath = "C:\\AndroidProjects\\NightOwlTracker\\app\\src\\main\\res\\drawable-xxxhdpi\\ic_account_box_grey600_24dp.png";
        mImageUrls.add(academicSessionsPath);
        mImageUrls.add(classesPath);
        mImageUrls.add(usersPath);


    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
