package com.example.nightowltracker.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.database.ClassEntity;
import com.example.nightowltracker.database.DBHelper;
import com.example.nightowltracker.database.EnrollmentEntity;
import com.example.nightowltracker.database.LineItemEntity;
import com.example.nightowltracker.database.UserEntity;
import com.example.nightowltracker.ui.RecyclerViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // vars
    private ArrayList<String> mNames = new ArrayList<>();

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

        // Starts the RecyclerView
        initRecyclerView();

        // Creates DB object
        myHelper = new DBHelper(MainActivity.this);
        myHelper.getWritableDatabase();  // creates or opens database
        Toast.makeText(MainActivity.this, myHelper.getDatabaseName() + " opened!", Toast.LENGTH_SHORT).show();

        // Initialize DB Tables
        AcademicSessionEntity academicSessionEntity = new AcademicSessionEntity();
        ArrayList<String> academicSessionTable = academicSessionEntity.createTableAcademicSession();

        // TODO rename Class to Course???  Is there a global rename?
        // TODO ClassEntity may have taken care of that
        ClassEntity classEntity = new ClassEntity();
        ArrayList<String> classTable = classEntity.createTableClass();

        EnrollmentEntity enrollment = new EnrollmentEntity();
        ArrayList<String> enrollmentTable = enrollment.createTableEnrollment();

        LineItemEntity lineItemEntity = new LineItemEntity();
        ArrayList<String> lineItemTable = lineItemEntity.createTableLineItem();

        UserEntity userEntity = new UserEntity();
        ArrayList<String> userTable = userEntity.createTableUser();

        // Create tables
        myHelper.createTable(academicSessionTable.get(0), academicSessionTable.get(1));
        myHelper.createTable(classTable.get(0), classTable.get(1));
        myHelper.createTable(enrollmentTable.get(0), enrollmentTable.get(1));
        myHelper.createTable(lineItemTable.get(0), lineItemTable.get(1));
        myHelper.createTable(userTable.get(0), userTable.get(1));

        // Set FKs strings
        String fk_class_sessionId = myHelper.setForeignKeyString("fk_class_sessionId", "sessionId", "AcademicSessionEntity", "sessionId");
        String fk_enrollment_userId = myHelper.setForeignKeyString("fk_enrollment_userId", "userId", "UserEntity", "userId");
        String fk_enrollment_classId = myHelper.setForeignKeyString("fk_enrollment_classId", "classId", "Class", "classId");
        String fk_lineItem_classId = myHelper.setForeignKeyString("fk_lineItem_classId", "classId", "Class", "classId");

        // Add FKs to tables
        myHelper.addFK(fk_class_sessionId);
        myHelper.addFK(fk_enrollment_userId);
        myHelper.addFK(fk_enrollment_classId);
        myHelper.addFK(fk_lineItem_classId);

        // This inserts every time the main activity opens-- proof of concept
        // boolean insert1 = myHelper.insertIntoAcademicSessionTable("Term 1", Date.valueOf("2019-09-04"), Date.valueOf("2019-12-20"));
    }

    @Override
    protected void onPause(){
        super.onPause();

//        myHelper.close();
//        Toast.makeText(MainActivity.this, myHelper.getDatabaseName() + " closed!", Toast.LENGTH_SHORT).show();
    }


    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
