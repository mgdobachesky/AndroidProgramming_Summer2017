package com.example.a001264912.lab_6;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Set request code
    private static final int REQUEST_CODE = 10;

    // Set initial controls
    private DatabaseHandler db;
    private EditText txtFirstName;
    private EditText txtLastName;
    private Spinner spnChocolateType;
    private EditText txtNumBars;
    private RadioGroup rdgShippingGroup;
    private RadioButton rdoShippingMethod;
    private EditText txtPrice;
    private Button btnSaveOrder;
    private TextView lblDisplayResults;
    private EditText txtGetOrder;
    private Button btnGetOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Assign views to variables
        txtFirstName = (EditText)findViewById(R.id.txtFirstName);
        txtLastName = (EditText)findViewById(R.id.txtLastName);
        spnChocolateType = (Spinner)findViewById(R.id.spnChocolateType);
        txtNumBars = (EditText)findViewById(R.id.txtNumBars);
        rdgShippingGroup = (RadioGroup)findViewById(R.id.rdgShipping);
        txtPrice = (EditText)findViewById(R.id.txtPrice);
        btnSaveOrder = (Button)findViewById(R.id.btnSaveOrder);
        lblDisplayResults = (TextView)findViewById(R.id.lblDisplayResults);
        txtGetOrder = (EditText)findViewById(R.id.txtGetOrder);
        btnGetOrder = (Button)findViewById(R.id.btnGetOrder);

        // Initialize the database
        db = new DatabaseHandler(this);

        // Create list to hold spinner items
        List<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add("Dark Chocolate");
        spinnerItems.add("Milk Chocolate");
        spinnerItems.add("Light Chocolate");

        // Configure adapter with spinner items list and set the spinner to use it
        ArrayAdapter<String> spinnerItemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerItemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnChocolateType.setAdapter(spinnerItemsAdapter);

        // Set action listeners
        btnSaveOrder.setOnClickListener(saveOrderOnClickListener);
        btnGetOrder.setOnClickListener(getOrderOnClickListener);
    }

    private OnClickListener saveOrderOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create new order to work with
            Order newOrder = new Order();

            // Set the order's properties
            newOrder.setFirstName(txtFirstName.getText().toString());
            newOrder.setLastName(txtLastName.getText().toString());
            newOrder.setChocolateType(spnChocolateType.getSelectedItem().toString());
            newOrder.setChocolateQuantity(Integer.parseInt(txtNumBars.getText().toString()));
            newOrder.setPrice(Float.parseFloat(txtPrice.getText().toString()));

            int shippingId = rdgShippingGroup.getCheckedRadioButtonId();
            rdoShippingMethod = (RadioButton)findViewById(shippingId);
            if(rdoShippingMethod.getText().equals("Expedited")) {
                newOrder.setExpeditedShipping(true);
            } else if(rdoShippingMethod.getText().equals("Normal")) {
                newOrder.setExpeditedShipping(false);
            }

            Intent i = new Intent(getApplicationContext(), com.example.a001264912.lab_6.ResultActivity.class);
            i.putExtra("chocolateOrder", newOrder);
            startActivityForResult(i, REQUEST_CODE);
        }
    };

    private OnClickListener getOrderOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Order requestedOrder = getOrderById(Integer.parseInt(txtGetOrder.getText().toString()));
            displayOrder(requestedOrder);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get data from ForResult activity and use it in this activity
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("numberOfOrders")) {
                String numberOfOrders = data.getExtras().getString("numberOfOrders");
                lblDisplayResults.setText("Number Of Orders = " + numberOfOrders);
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

    public Order getOrderById(int orderId) {
        return db.getOrder(orderId);
    }

    public void displayOrder(Order requestedOrder) {
        // Initialize some variables
        int selectedChocolateType = 0;
        int selectedShippingType = 0;

        // Prepare the information needed to set an order
        ArrayAdapter<String> spinnerAdapter = (ArrayAdapter<String>)spnChocolateType.getAdapter();
        selectedChocolateType = spinnerAdapter.getPosition(requestedOrder.getChocolateType());

        if(requestedOrder.isExpeditedShipping()) {
            selectedShippingType = R.id.rdoShipExpedited;
        } else if(!requestedOrder.isExpeditedShipping()) {
            selectedShippingType = R.id.rdoShipNormal;
        }

        // Set controls to be of the information in the requested order
        txtFirstName.setText(requestedOrder.getFirstName());
        txtLastName.setText(requestedOrder.getLastName());
        spnChocolateType.setSelection(selectedChocolateType);
        txtNumBars.setText(Integer.toString(requestedOrder.getChocolateQuantity()));
        rdgShippingGroup.check(selectedShippingType);
        txtPrice.setText(String.format("%.2f", requestedOrder.getPrice()));
    }
}
