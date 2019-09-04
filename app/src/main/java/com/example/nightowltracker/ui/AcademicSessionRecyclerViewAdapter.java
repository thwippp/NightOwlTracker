package com.example.nightowltracker.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.activities.AcademicSessionEditorActivity;
import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.utilities.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AcademicSessionRecyclerViewAdapter extends RecyclerView.Adapter<AcademicSessionRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "ASRecyclerViewAdapter";

    private final List<AcademicSessionEntity> mAcademicSession;
    private final Context mContext;


    // TODO create a generic adapter then cast it to the type of entity?
    // TODO or make all of your data types into one "note" type so the handler can handle all of them?
    public AcademicSessionRecyclerViewAdapter(List<AcademicSessionEntity> mAcademicSession, Context mContext) {
        this.mAcademicSession = mAcademicSession;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        final AcademicSessionEntity academicSession = mAcademicSession.get(position);
        holder.mTextView.setText(academicSession.getTitle());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AcademicSessionEditorActivity.class);
                intent.putExtra(Constants.ACADEMIC_SESSION_ID_KEY, academicSession.getSessionId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAcademicSession.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        // @BindView(R.id.main_fab)
        // FloatingActionButton mFab;
        FloatingActionButton mFab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.recycler_view_textView);
            mFab = itemView.findViewById(R.id.recycler_view_fab);
        }
    }
}
