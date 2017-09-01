package com.example.a001264912.lab_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {
    // Set request code
    private static final int REQUEST_CODE = 10;

    // Set initial controls
    private EditText firstName;
    private EditText lastName;
    private Spinner chocolateType;
    private EditText numBars;
    private RadioGroup shippingGroup;
    private RadioButton shippingMethod;
    private Button saveOrder;
    private TextView displayResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        firstName = (EditText)findViewById(R.id.txtFirstName);
        lastName = (EditText)findViewById(R.id.txtLastName);
        chocolateType = (Spinner)findViewById(R.id.spnChocolateType);
        numBars = (EditText)findViewById(R.id.txtNumBars);
        shippingGroup = (RadioGroup)findViewById(R.id.rdgShipping);
        saveOrder = (Button)findViewById(R.id.btnSaveOrder);
        displayResults = (TextView)findViewById(R.id.lblDisplayResults);

        // Create list to hold spinner items
        List<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add("Dark Chocolate");
        spinnerItems.add("Milk Chocolate");
        spinnerItems.add("Light Chocolate");

        // Configure adapter with spinner items list and set the spinner to use it
        ArrayAdapter<String> spinnerItemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerItemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chocolateType.setAdapter(spinnerItemsAdapter);

        // Set action listeners
        saveOrder.setOnClickListener(ResultClickListener);
    }

    private View.OnClickListener ResultClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create new order to work with
            Order newOrder = new Order();

            // Set the order's properties
            newOrder.setFirstName(firstName.getText().toString());
            newOrder.setLastName(lastName.getText().toString());
            newOrder.setChocolateType(chocolateType.getSelectedItem().toString());
            newOrder.setChocolateQuantity(Integer.parseInt(numBars.getText().toString()));

            int shippingId = shippingGroup.getCheckedRadioButtonId();
            shippingMethod = (RadioButton)findViewById(shippingId);
            if(shippingMethod.getText().equals("Expedited")) {
                newOrder.setExpeditedShipping(true);
            } else if(shippingMethod.getText().equals("Normal")) {
                newOrder.setExpeditedShipping(false);
            }

            Intent i = new Intent(getApplicationContext(), com.example.a001264912.lab_2.ResultActivity.class);
            i.putExtra("chocolateOrder", newOrder);
            startActivityForResult(i, REQUEST_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get data from ForResult activity and use it in this activity
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("numberOfOrders")) {
                String numberOfOrders = data.getExtras().getString("numberOfOrders");
                displayResults.setText("Number Of Orders = " + numberOfOrders);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
}
