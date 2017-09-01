package com.example.a001264912.lab_6;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    // Set initial controls
    private DatabaseHandler db;
    private Button btnSendBack;
    private ListView lstResults;
    private TextView txtSearchByPrice;
    private Button btnSearchByPrice;

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
        btnSendBack = (Button)findViewById(R.id.btnSendBack);
        lstResults = (ListView)findViewById(R.id.lstResults);
        txtSearchByPrice = (TextView)findViewById(R.id.txtSearchByPrice);
        btnSearchByPrice = (Button)findViewById(R.id.btnSearchByPrice);

        // Set OnClickListener
        btnSendBack.setOnClickListener(sendBackOnClickListener);
        btnSearchByPrice.setOnClickListener(searchByPriceOnClickListener);

        // Create an instance of the database
        db = new DatabaseHandler(this);

        // Create a Bundle to hold the sending intent's extras
        Bundle extras = getIntent().getExtras();

        // Get the parcelable Order from the sending intent and add it to a new Order
        Order newOrder = extras.getParcelable("chocolateOrder");

        addOrder(newOrder);
        populateOrdersList(getAllOrders());

    }

    @Override
    public void finish() {
        // Send Order count back to the MainActivity
        Intent intent = new Intent();
        int data = getOrdersCount();
        String dataString = Integer.toString(data);
        intent.putExtra("numberOfOrders", dataString);
        setResult(RESULT_OK, intent);
        super.finish();
    }

    private View.OnClickListener sendBackOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener searchByPriceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            float minimumPrice = Float.parseFloat(txtSearchByPrice.getText().toString());
            populateOrdersList(getOrdersByPrice(minimumPrice));
        }
    };

    private void populateOrdersList(List<Order> orderList) {
        // Get a count of how many orders exist
        int ordersCount = orderList.size();

        // Define an adapter for the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        // Adding entries in List
        for(int counter = 0; counter < ordersCount; counter++) {
            adapter.add(String.format(orderList.get(counter).getFirstName()
                    + " " + orderList.get(counter).getLastName()
                    + " id = " + orderList.get(counter).getId()
                    + String.format(" - $%.2f", orderList.get(counter).getPrice())));
        }

        // Setting adapter to list
        lstResults.setAdapter(adapter);
    }

    private ArrayList<Order> getAllOrders() {
        return new ArrayList<Order>(db.getAllOrders());
    }

    private ArrayList<Order> getOrdersByPrice(float minimumPrice) {
        return new ArrayList<Order>(db.getOrder(minimumPrice));
    }

    private void addOrder(Order newOrder) {
        // Add order to the database
        db.addOrder(newOrder);
    }

    private int getOrdersCount() {
        return db.getOrdersCount();
    }

}
