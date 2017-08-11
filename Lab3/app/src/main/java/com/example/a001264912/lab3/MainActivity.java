package com.example.a001264912.lab3;

import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import android.view.View.OnClickListener;
import android.widget.Toast;

import static android.R.attr.value;
import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private Spinner chocolateType;
    private EditText numBars;
    private RadioGroup shippingGroup;
    private RadioButton shippingMethod;
    private Button saveOrder;
    private TextView displayResults;
    private ArrayList<Order> chocolateOrder = new ArrayList<Order>();

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

    private OnClickListener ResultClickListener = new OnClickListener() {
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

            // Add order to list of orders
            chocolateOrder.add(newOrder);

            // Print acknowledgement message
            displayResults.setText("Order added, there are now " + chocolateOrder.size() + " orders.");
        }
    };

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
        } else if(id == R.id.action_new || id == R.id.action_new2) {
            // Get the controls to be cleared
            firstName = (EditText)findViewById(R.id.txtFirstName);
            lastName = (EditText)findViewById(R.id.txtLastName);
            chocolateType = (Spinner)findViewById(R.id.spnChocolateType);
            numBars = (EditText)findViewById(R.id.txtNumBars);
            shippingGroup = (RadioGroup)findViewById(R.id.rdgShipping);
            displayResults = (TextView)findViewById(R.id.lblDisplayResults);

            // Clear each control
            firstName.setText("");
            lastName.setText("");
            chocolateType.setSelection(0);
            numBars.setText("");
            shippingGroup.clearCheck();
            displayResults.setText("");

        } else if(id == R.id.action_doubleOrder) {
            // Get value number of chocolate bars in order and double it
            int barValue = 0;
            numBars = (EditText)findViewById(R.id.txtNumBars);
            barValue = Integer.parseInt(numBars.getText().toString());
            barValue = barValue * 2;
            numBars.setText(Integer.toString(barValue));

        } else if(id == R.id.action_getFirstOrder) {
            // Initialize variables
            int selectedChocolateType = 0;
            int selectedShippingType = 0;

            // Get controls to be set to the values in first order
            firstName = (EditText)findViewById(R.id.txtFirstName);
            lastName = (EditText)findViewById(R.id.txtLastName);
            chocolateType = (Spinner)findViewById(R.id.spnChocolateType);
            numBars = (EditText)findViewById(R.id.txtNumBars);
            shippingGroup = (RadioGroup)findViewById(R.id.rdgShipping);
            displayResults = (TextView)findViewById(R.id.lblDisplayResults);

            // Get first order in ArrayList
            Order firstOrder = chocolateOrder.get(0);

            // Create adapter to use in getting first order's selected spinner item
            ArrayAdapter<String> spinnerAdapter = (ArrayAdapter<String>)chocolateType.getAdapter();
            // Get position of the first order's selected spinner item
            selectedChocolateType = spinnerAdapter.getPosition(firstOrder.getChocolateType());

            // Get position of selected radio button
            if(firstOrder.isExpeditedShipping()) {
                selectedShippingType = R.id.rdoShipExpedited;
            } else if(!firstOrder.isExpeditedShipping()) {
                selectedShippingType = R.id.rdoShipNormal;
            }

            // Set the items selected to be that of the first order
            firstName.setText(firstOrder.getFirstName());
            lastName.setText(firstOrder.getLastName());
            chocolateType.setSelection(selectedChocolateType);
            numBars.setText(Integer.toString(firstOrder.getChocolateQuantity()));
            shippingGroup.check(selectedShippingType);
            displayResults.setText("");
        }

        return super.onOptionsItemSelected(item);
    }
}
