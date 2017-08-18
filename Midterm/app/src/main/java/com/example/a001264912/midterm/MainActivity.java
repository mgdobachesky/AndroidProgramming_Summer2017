package com.example.a001264912.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 10;

    private EditText txtAmount;
    private RadioGroup rdgBank;
    private TextView lblResult;

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
        } else if (id == R.id.action_submit) {
            txtAmount = (EditText)findViewById(R.id.txtAmount);
            rdgBank = (RadioGroup)findViewById(R.id.rdgBank);
            lblResult = (TextView)findViewById(R.id.lblResults);

            int bankId = rdgBank.getCheckedRadioButtonId();
            RadioButton selectedOperation = (RadioButton)findViewById(bankId);

            String amountString = txtAmount.getText().toString();
            String operationType = selectedOperation.getText().toString();

            Intent i = new Intent(getApplicationContext(), com.example.a001264912.midterm.ResultActivity.class);
            i.putExtra("amountString", amountString);
            i.putExtra("operationType", operationType);
            startActivityForResult(i, REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if(data.hasExtra("balance")) {
                String balanceData = data.getExtras().getString("balance");
                lblResult.setText("Current Balance: $" + balanceData);
            }
        }
    }
}
