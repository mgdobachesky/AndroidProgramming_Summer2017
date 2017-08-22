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

    private final Activity context;
    private  final String[] itemName;
    private final Uri[] imgUri;
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
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);
        TextView txtTitle = (TextView)rowView.findViewById(R.id.txtListName);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.imgListIcon);
        final ImageView deleteImage = (ImageView)rowView.findViewById(R.id.imgListDelete);

        txtTitle.setText(itemName[position]);
        imageView.setImageBitmap(scaleImage(imgUri[position]));

        deleteImage.setOnClickListener(new View.OnClickListener() {
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

    private Bitmap scaleImage(Uri imageUri) {
        // Get the dimensions of the View
        int targetW = 64;
        int targetH = 64;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageUri.getPath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath(), bmOptions);
        return bitmap;
    }

    public interface AdapterCallback {
        void onMethodCallback(Uri imgUri);
    }

}
