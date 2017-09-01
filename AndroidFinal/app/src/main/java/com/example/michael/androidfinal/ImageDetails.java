package com.example.michael.androidfinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.JsonReader;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ibm.watson.developer_cloud.http.HttpMediaType.JSON;

public class ImageDetails extends AppCompatActivity implements AsyncResponse {

    // Define controls (global)
    ImageView imgImageForDetails;
    TextView lblImageDetails;

    // Define member variables
    String filePath;
    RecognizeImage recognizeImage = new RecognizeImage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize controls
        imgImageForDetails = (ImageView)findViewById(R.id.imgImageForDetails);
        lblImageDetails = (TextView)findViewById(R.id.lblImageDetails);

        // Get file path from extra intent data
        Bundle extra = this.getIntent().getExtras();
        filePath = extra.getString("picturePath");

        // Set the image to be the selected image
        imgImageForDetails.setImageBitmap(scaleImage(Uri.parse(filePath)));

        // Set delegate for async task to be this
        // so the method of this class will run.
        // Then execute the async task
        recognizeImage.delegate = this;
        recognizeImage.execute(filePath);
    }

    @Override
    public void processFinish(VisualClassification imageClassifiers) {
        try {
            // Get the JSON response
            JSONObject jsonObject = new JSONObject(imageClassifiers.toString());

            // Continue only if the json object is not null
            if(!jsonObject.isNull("images")){

                // Check to see if there is an error in the JSON response
                String isError = jsonObject.optJSONArray("images").optJSONObject(0).optString("error");

                // Continue if there was no error
                if (isError == "") {
                    JSONArray jsonData = jsonObject.getJSONArray("images").getJSONObject(0).getJSONArray("classifiers").getJSONObject(0).getJSONArray("classes");

                    StringBuffer imageBuffer = new StringBuffer();

                    for (int i = 0; i < jsonData.length(); i++) {
                        JSONObject imageDetails = jsonData.getJSONObject(i);
                        String imageClass = imageDetails.getString("class");
                        String imageTypeHierarchy = imageDetails.optString("type_hierarchy");
                        String imageScore = imageDetails.optString("score");

                        imageBuffer.append("Definition Number ");
                        imageBuffer.append(i + 1);
                        imageBuffer.append("\n");
                        imageBuffer.append("Class: ");
                        imageBuffer.append(imageClass);
                        imageBuffer.append("\n");
                        imageBuffer.append("Score: ");
                        imageBuffer.append(imageScore);
                        imageBuffer.append("\n");
                        if (!imageTypeHierarchy.isEmpty()) {
                            imageBuffer.append("Type Hierarchy: ");
                            imageBuffer.append(imageTypeHierarchy);
                            imageBuffer.append("\n");
                        }
                        imageBuffer.append("\n");
                    }
                    lblImageDetails.setText(imageBuffer);
                } else {
                    lblImageDetails.setText("Error analyzing image.");
                }
            }

        } catch(JSONException e){
            // Handle JSONException
        }
    }

    private Bitmap scaleImage(Uri imageUri) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        // Get the dimensions of the View
        int targetW = height/4;
        int targetH = width/4;

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
}
