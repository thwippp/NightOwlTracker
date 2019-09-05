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
import com.example.nightowltracker.activities.LineItemEditorActivity;
import com.example.nightowltracker.database.LineItemEntity;
import com.example.nightowltracker.utilities.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LineItemRecyclerViewAdapter extends RecyclerView.Adapter<LineItemRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "LIRecyclerViewAdapter";

    private final List<LineItemEntity> mLineItem;
    private final Context mContext;

    public LineItemRecyclerViewAdapter(List<LineItemEntity> mLineItem, Context mContext) {
        this.mLineItem = mLineItem;
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

        final LineItemEntity LineItem = mLineItem.get(position);
        holder.mTextView.setText(LineItem.getTitle());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LineItemEditorActivity.class);
                intent.putExtra(Constants.LINE_ITEM_ID, LineItem.getLineItemId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLineItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        FloatingActionButton mFab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.recycler_view_textView);
            mFab = itemView.findViewById(R.id.recycler_view_fab);
        }
    }
}
