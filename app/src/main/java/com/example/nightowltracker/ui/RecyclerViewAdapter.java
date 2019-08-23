package com.example.nightowltracker.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nightowltracker.R;
import com.example.nightowltracker.activities.AcademicSessionActivity;
import com.example.nightowltracker.activities.ClassActivity;
import com.example.nightowltracker.activities.MainActivity;
import com.example.nightowltracker.activities.UserActivity;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mTitle = new ArrayList<>();
    private Context mContext;
    private Context context;
//    private Cursor mCursor;


    public RecyclerViewAdapter(Context mContext, ArrayList<String> mTitle) {
        this.mTitle = mTitle;
        this.mContext = mContext;
    }

//    public RecyclerViewAdapter(Context mContext, Cursor mCursor){
//        this.mContext = mContext;
//        this.mCursor = mCursor;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");


        holder.text.setText(mTitle.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mTitle.get(position));

                // TODO Find a way to switch activities based on name of item clicked
                Intent intent;

                switch (mTitle.get(position)) {
                    case "Terms":
                        intent = new Intent(context, AcademicSessionActivity.class);
                        break;
                    case "Classes":
                        intent = new Intent(context, ClassActivity.class);
                        break;
                    case "Users":
                        intent = new Intent(context, UserActivity.class);
                        break;
                    default:
                        intent = new Intent(context, MainActivity.class);
                        break;
                }
                context.startActivity(intent);

                Toast.makeText(mContext, mTitle.get(position), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            text = itemView.findViewById(R.id.recycler_view_textView);
            parentLayout = itemView.findViewById(R.id.linearLayout);
        }
    }


}
