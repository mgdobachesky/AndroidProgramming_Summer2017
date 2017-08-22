package com.example.michael.androidfinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by michael on 8/21/17.
 */

public class RecognizeImage extends AsyncTask<String, Integer, VisualClassification> {

    public AsyncResponse delegate = null;

    @Override
    protected VisualClassification doInBackground(String... filePath) {
        // Instantiate the service and set the API key
        VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey("{api-key}");

        ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                .images(new File(filePath[0]))
                .build();
        VisualClassification result = service.classify(options).execute();

        return result;
    }

    @Override
    protected void onPostExecute(VisualClassification visualClassification) {
        super.onPostExecute(visualClassification);
        delegate.processFinish(visualClassification);
    }
}
