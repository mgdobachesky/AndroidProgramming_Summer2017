package com.example.michael.androidfinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.IDNA;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import java.security.*;

public class MainActivity extends AppCompatActivity implements CustomListAdapter.AdapterCallback {

    // Initialize static final variables (global)
    private static final int REQUEST_TAKE_PHOTO = 1;

    // Define controls (global)
    private ListView lstPictures;

    // Define member variables
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(takePictureOnClickListener);

        // Initialize controls
        lstPictures = (ListView)findViewById(R.id.lstPictures);

        // Set event listeners
        lstPictures.setOnItemClickListener(pictureOnItemClickListener);
    }

    private View.OnClickListener takePictureOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dispatchTakePictureIntent();
        }
    };

    private AdapterView.OnItemClickListener pictureOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String item = lstPictures.getItemAtPosition(i).toString();
            sendVisualRecognition(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + item);
        }
    };

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("IMAGE CREATION FAILED", ex.toString());
            }
            // Continue only if the File was successfully created
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.michael.androidfinal", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //takePictureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 2_000_000);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void populatePicturesList() {
        // Get existing pictures and list them
        File pictures = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
        File[] pictureList = pictures.listFiles();
        int picturesSize = pictureList.length;

        String[] pictureNames = new String[picturesSize];
        Uri[] pictureUris = new Uri[picturesSize];

        int i = 0;
        for(File picture : pictureList) {
            pictureNames[i] = picture.getName();
            pictureUris[i] = Uri.parse(picture.getAbsolutePath());
            i++;
        }

        CustomListAdapter userPicturesAdapter = new CustomListAdapter(this, pictureNames, pictureUris);

        lstPictures.setAdapter(userPicturesAdapter);
    }

    private void sendVisualRecognition(String imagePath) {
        Intent imageDetails = new Intent("PICTURE_DETAILS");
        Bundle extra = new Bundle();
        extra.putString("picturePath", imagePath);
        imageDetails.putExtras(extra);
        startActivity(imageDetails);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        populatePicturesList();

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Work with photo that was just saved
            sendVisualRecognition(mCurrentPhotoPath);
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == 0) {
            new File(mCurrentPhotoPath).delete();
        }
    }

    @Override
    public void onMethodCallback(Uri imgUri) {
        File fileToDelete = new File(imgUri.getPath());
        boolean didDelete = fileToDelete.delete();

        if(didDelete) {
            // Reload pictures list after deleting picture
            this.onResume();
        }
    }
}
