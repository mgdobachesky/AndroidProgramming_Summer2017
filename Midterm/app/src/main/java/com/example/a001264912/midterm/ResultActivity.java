package com.example.a001264912.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Integer.parseInt;
import static java.nio.file.Paths.get;

public class ResultActivity extends AppCompatActivity {
    public static float Balance;

    private TextView lblDeposit;
    private TextView lblBalance;
    private Button btnClose;

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

        lblDeposit = (TextView)findViewById(R.id.lblDeposit);
        lblBalance = (TextView)findViewById(R.id.lblBalance);
        btnClose = (Button)findViewById(R.id.btnClose);

        btnClose.setOnClickListener(closeOnClickListener);

        Bundle extras = getIntent().getExtras();
        String amountString = extras.get("amountString").toString();
        String operationType = extras.get("operationType").toString();

        if(operationType.equals("Withdrawal")) {
            if(Balance >= Float.parseFloat(amountString)) {
                lblDeposit.setText("Withdrawal: $" + amountString);
                Balance = Balance - Float.parseFloat(amountString);
                lblBalance.setText("Balance: $" + Float.toString(Balance));
            } else {
                lblDeposit.setText("Not enough balance to withdraw $" + amountString);
                lblBalance.setText("Balance: $" + Float.toString(Balance));
            }
        } else if(operationType.equals("Deposit")) {
            lblDeposit.setText("Deposit: $" + amountString);
            Balance = Balance + Float.parseFloat(amountString);
            lblBalance.setText("Balance: $" + Float.toString(Balance));
        } else if(operationType.equals("Inquiry")) {
            lblDeposit.setText("Inquiry");
            lblBalance.setText("Balance: $" + Float.toString(Balance));
        }
    }

    private View.OnClickListener closeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @Override
    public void finish() {
        Intent i = new Intent();
        String data = Float.toString(Balance);
        i.putExtra("balance", data);
        setResult(RESULT_OK, i);
        super.finish();
    }

}
