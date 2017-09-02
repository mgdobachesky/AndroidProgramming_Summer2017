package com.example.michael.androidfinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

/**
 * Created by michael on 8/20/17.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] itemName;
    private Uri[] imgUri;
    private AdapterCallback mAdapterCallback;

    public CustomListAdapter(Activity context, String[] itemName, Uri[] imgUri) {
        super(context, R.layout.mylist, itemName);
        this.context = context;
        this.itemName = itemName;
        this.imgUri = imgUri;
        try {
            mAdapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }

    }

    public View getView(final int position, View view, final ViewGroup parent) {
        View rowView = view;
        ItemInformationHolder holder = null;

        if(rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.mylist, null, true);

            holder = new ItemInformationHolder();
            holder.txtTitle = (TextView) rowView.findViewById(R.id.txtListName);
            holder.imageView = (ImageView) rowView.findViewById(R.id.imgListIcon);
            holder.deleteImage = (ImageView) rowView.findViewById(R.id.imgListDelete);

            rowView.setTag(holder);
        } else {
            holder = (ItemInformationHolder) rowView.getTag();
        }

        holder.txtTitle.setText(itemName[position]);
        Bitmap bitmap = ScaleImage.scaleImage(imgUri[position], 64, 64);
        holder.imageView.setImageBitmap(bitmap);

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mAdapterCallback.onMethodCallback(imgUri[position]);
                } catch(ClassCastException e) {
                    throw new ClassCastException("Problem setting AdapterCallback.");
                }
            }
        });

        return rowView;
    }

    static class ItemInformationHolder {
        TextView txtTitle;
        ImageView imageView;
        ImageView deleteImage;
    }

    public interface AdapterCallback {
        void onMethodCallback(Uri imgUri);
    }

}
