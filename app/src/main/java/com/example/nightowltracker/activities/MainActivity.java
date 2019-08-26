package com.example.nightowltracker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.database.DBHelper;
import com.example.nightowltracker.ui.RecyclerViewAdapter;
import com.example.nightowltracker.view_model.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // vars
    private MainViewModel mViewModel;
    private ArrayList<String> mNames = new ArrayList<>();  // TODO DELETE ME
    private List<AcademicSessionEntity> asData = new ArrayList<>();


    // Instance variable for SQLite DB Helper Object
    DBHelper myHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO fab on each list item
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Context context = fab.getContext();
                context.startActivity(intent);
            }
        });


        //Init view model
        initViewModel();

        // Starts the RecyclerView
        initRecyclerView();

        asData.addAll(mViewModel.mAcademicSession);
        for (AcademicSessionEntity ase : asData) {
            Log.i("AcademicSessionData", ase.toString());
        }

//        Log.d(TAG, "onCreate: started.");
//        mNames.add("Terms");
//        mNames.add("Classes");
//        mNames.add("Users");
//
//        // Creates DB object
//        myHelper = new DBHelper(MainActivity.this);
//        myHelper.getWritableDatabase();  // creates or opens database
//        Toast.makeText(MainActivity.this, myHelper.getDatabaseName() + " opened!", Toast.LENGTH_SHORT).show();
//
//        // Initialize DB Tables
//        AcademicSessionEntity academicSessionEntity = new AcademicSessionEntity();
//        ArrayList<String> academicSessionTable = academicSessionEntity.createTableAcademicSession();
//
//        // TODO rename Class to Course???  Is there a global rename?
//        // TODO ClassEntity may have taken care of that
//        // TODO move all BL to view_models
//        // TODO finish creating all ENTITIES and DAO Classes
//
//        ClassEntity classEntity = new ClassEntity();
//        ArrayList<String> classTable = classEntity.createTableClass();
//
//        EnrollmentEntity enrollment = new EnrollmentEntity();
//        ArrayList<String> enrollmentTable = enrollment.createTableEnrollment();
//
//        LineItemEntity lineItemEntity = new LineItemEntity();
//        ArrayList<String> lineItemTable = lineItemEntity.createTableLineItem();
//
//        UserEntity userEntity = new UserEntity();
//        ArrayList<String> userTable = userEntity.createTableUser();
//
//        // Create tables
//        myHelper.createTable(academicSessionTable.get(0), academicSessionTable.get(1));
//        myHelper.createTable(classTable.get(0), classTable.get(1));
//        myHelper.createTable(enrollmentTable.get(0), enrollmentTable.get(1));
//        myHelper.createTable(lineItemTable.get(0), lineItemTable.get(1));
//        myHelper.createTable(userTable.get(0), userTable.get(1));
//
//        // Set FKs strings
//        String fk_class_sessionId = myHelper.setForeignKeyString("fk_class_sessionId", "sessionId", "AcademicSessionEntity", "sessionId");
//        String fk_enrollment_userId = myHelper.setForeignKeyString("fk_enrollment_userId", "userId", "UserEntity", "userId");
//        String fk_enrollment_classId = myHelper.setForeignKeyString("fk_enrollment_classId", "classId", "Class", "classId");
//        String fk_lineItem_classId = myHelper.setForeignKeyString("fk_lineItem_classId", "classId", "Class", "classId");
//
//        // Add FKs to tables
//        myHelper.addFK(fk_class_sessionId);
//        myHelper.addFK(fk_enrollment_userId);
//        myHelper.addFK(fk_enrollment_classId);
//        myHelper.addFK(fk_lineItem_classId);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
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
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, asData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
