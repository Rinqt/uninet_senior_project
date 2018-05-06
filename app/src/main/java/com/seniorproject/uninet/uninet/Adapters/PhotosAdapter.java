package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.seniorproject.uninet.uninet.ConstructorClasses.Photos;
import com.seniorproject.uninet.uninet.R;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<Photos> mList;
    private Context mContext;

    public PhotosAdapter(Context mContext, List<Photos> mList)
    {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View photosView;

        photosView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_template, parent, false);
        return new UserPhotos(photosView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Photos post = mList.get(position);

        if (post != null)
        {
            if (post.getPhoto() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(post.getPhoto(), 0, post.getPhoto().length);
                ((UserPhotos) holder).singlePhoto.setImageBitmap(bitmap);
            }
            else
                ((UserPhotos) holder).singlePhoto.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public class UserPhotos extends RecyclerView.ViewHolder
    {
        ImageView singlePhoto;


        UserPhotos(View itemView)
        {
            super(itemView);
            singlePhoto = itemView.findViewById(R.id.single_photo);
        }
    }
}
