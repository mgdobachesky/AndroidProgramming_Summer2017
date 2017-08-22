package com.example.michael.androidfinal;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

/**
 * Created by michael on 8/21/17.
 */

public interface AsyncResponse {
    void processFinish(VisualClassification output);
}
