package com.example.a001264912.lab_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.data;

public class ResultActivity extends AppCompatActivity {

    // Create static list of Order to store data in
    // NOTE: static won't change when intent closes
    static ArrayList<Order> CandyOrders = new ArrayList<Order>();

    // Set initial controls
    private Button sendBack;
    private ListView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Assign views to variables
        sendBack = (Button)findViewById(R.id.btnSendBack);
        results = (ListView)findViewById(R.id.lstResults);

        // Set OnClickListener
        sendBack.setOnClickListener(sendBackOnClickListener);

        // Create a Bundle to hold the sending intent's extras
        Bundle extras = getIntent().getExtras();

        // Get the parcelable Order from the sending intent and add it to the Orders ArrayList
        Order inputOrder = extras.getParcelable("chocolateOrder");
        CandyOrders.add(inputOrder);

        // Defining Adapter for List content

        // Simple_list_item_1 contains only a TextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        // Adding entries in List
        for(int counter = 0; counter < CandyOrders.size(); counter++) {
            adapter.add(CandyOrders.get(counter).getFirstName() + " - " + CandyOrders.get(counter).getLastName() + " - " + CandyOrders.get(counter).getChocolateType());
        }

        // Setting adapter to list
        results.setAdapter(adapter);
    }

    private View.OnClickListener sendBackOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @Override
    public void finish() {
        // Send Order count back to the MainActivity
        Intent intent = new Intent();
        int data = CandyOrders.size();
        String dataString = Integer.toString(data);
        intent.putExtra("numberOfOrders", dataString);
        setResult(RESULT_OK,intent);
        super.finish();
    }

}
