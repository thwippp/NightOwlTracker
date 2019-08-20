package com.example.nightowltracker.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nightowltracker.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter( Context mContext, ArrayList<String> mImageNames, ArrayList<String> mImages) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        // Insert image into imageView
//        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);
//        String path = "C:\\AndroidProjects\\NightOwlTracker\\app\\src\\main\\res\\drawable-hdpi\\ic_calendar_range_grey600_24dp.png";
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            Icon icon = Icon.createWithFilePath(path);
//            holder.image.setImageIcon(icon);
//        }
//        .setImageDrawable(Drawable.createFromPath(path));
//        holder.image.setImageURI(mImages.get(position));

        //TODO how to set insert images into imageview
        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);

        holder.text.setText(mImageNames.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));
                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

        @Override
        public int getItemCount(){
            return mImageNames.size();
        }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView text;
        LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView1);
            text = itemView.findViewById(R.id.textView1);
            parentLayout = itemView.findViewById(R.id.linearLayout);
        }
    }


}
