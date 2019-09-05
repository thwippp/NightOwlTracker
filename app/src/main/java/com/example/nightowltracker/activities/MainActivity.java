package com.example.nightowltracker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nightowltracker.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Image Views
        final ImageView asiv = findViewById(R.id.image_view_academic_session);
        final ImageView civ = findViewById(R.id.image_view_class);
        final ImageView uiv = findViewById(R.id.image_view_user);
        final ImageView liiv = findViewById(R.id.image_view_line_item);


        asiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AcademicSessionMainActivity.class);
                Context context = asiv.getContext();
                context.startActivity(intent);
            }
        });

        civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClassMainActivity.class);
                Context context = civ.getContext();
                context.startActivity(intent);
            }
        });

        liiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LineItemMainActivity.class);
                Context context = liiv.getContext();
                context.startActivity(intent);
            }
        });

//        uiv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, UserMainActivity.class);
//                Context context = uiv.getContext();
//                context.startActivity(intent);
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_go_to_academic_session) {
            Intent intent = new Intent(MainActivity.this, AcademicSessionMainActivity.class);
            Context context = this;
            context.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
