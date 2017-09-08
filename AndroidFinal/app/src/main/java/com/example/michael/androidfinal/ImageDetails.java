package com.example.michael.androidfinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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

public class ImageDetails extends AppCompatActivity {

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

        // Get Display Metrics
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels/3;
        int width = displayMetrics.widthPixels/3;

        // Set the image to be the selected image
        Bitmap bitmap = ScaleImage.scaleImage(Uri.parse(filePath), width, height);
        imgImageForDetails.setImageBitmap(bitmap);

        // Set delegate for async task to be this
        // so the method of this class will run.
        // Then execute the async task
        recognizeImage.execute(filePath);
    }

    private class RecognizeImage extends AsyncTask<String, Void, Void> {

        private String error = null;
        private String content;

        @Override
        protected void onPreExecute() {
            lblImageDetails.setText("Fetching data, please wait...");
        }

        @Override
        protected Void doInBackground(String... filePath) {
            try {
                // Instantiate the service and set the API key
                VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
                service.setApiKey("{api-key}");

                // Check if the image is small enough for the API to process
                File picture = new File(filePath[0]);
                long pictureLength = picture.length();
                if(pictureLength >= 2097152) {
                    error = "Image size limit exceeded.";
                }

                ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                        .images(picture)
                        .build();
                VisualClassification response = service.classify(options).execute();
                content = response.toString();
            } catch(Exception ex) {
                error = ex.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            if(error != null) {
                lblImageDetails.setText("ERROR: " + error);
            } else {
                String outputData = "";
                JSONObject jsonResponse;

                try {
                    // Get the JSON response
                    jsonResponse = new JSONObject(content);

                    // Handle main json data
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("images").optJSONObject(0).optJSONArray("classifiers").optJSONObject(0).optJSONArray("classes");
                    int jsonLength = jsonMainNode.length();

                    // Handle each piece of data
                    StringBuffer imageBuffer = new StringBuffer();
                    for(int i = 0; i < jsonLength; i++) {
                        JSONObject imageDetails = jsonMainNode.optJSONObject(i);
                        String imageClass = imageDetails.optString("class");
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

                    // Set image details
                    lblImageDetails.setText(imageBuffer);
                } catch(JSONException ex) {
                    lblImageDetails.setText("Error analyzing image.");
                    //ex.printStackTrace();
                }
            }
        }
    }

}
