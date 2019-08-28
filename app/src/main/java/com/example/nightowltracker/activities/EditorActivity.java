package com.example.nightowltracker.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.nightowltracker.R;
import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.view_model.EditorViewModel;

import static com.example.nightowltracker.utilities.Constants.ACADEMIC_SESSION_ID_KEY;

public class EditorActivity extends AppCompatActivity {

    // Butterknife
    /*
    @BindView (R.id.item_text)
    TextView mTextView;
     */
    private TextView mTextView; // = findViewById(R.id.item_text);

    private EditorViewModel mViewModel;
    private boolean mNewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check_white_36dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextView = findViewById(R.id.item_text);

        initViewModel();

    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(EditorViewModel.class);

        mViewModel.mLiveAs.observe(this, new Observer<AcademicSessionEntity>() {
            @Override
            public void onChanged(AcademicSessionEntity academicSessionEntity) {
                if (academicSessionEntity != null) {
                    mTextView.setText(academicSessionEntity.getTitle());
                }
            }
        });


        // TODO Look here!
        // The bundle is listing "Data" rather than "AcademicSession" or "note".  However, you may just choose to do "data"
        // or pass in the title of the type of object from before (check out the else clause)
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            // brand new Data
            setTitle("New Data");
            mNewData = true;
        } else {
            setTitle("Edit Data");
            int sessionId = extras.getInt(ACADEMIC_SESSION_ID_KEY);
            mViewModel.loadData(sessionId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
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
        mViewModel.saveData(mTextView.getText().toString());
        finish();
    }
}
