package com.example.intentpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.data;

public class ResultActivity extends AppCompatActivity {

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

        Bundle extras = getIntent().getExtras();
        String inputstring = extras.getString("yourkey");
        TextView myView = (TextView)findViewById(R.id.textView);
        myView.setText(inputstring);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        EditText data = (EditText)findViewById(R.id.txtEditTxt);
        String dataString = data.getText().toString();
        intent.putExtra("returnkey", dataString);
        setResult(RESULT_OK,intent);

        super.finish();
    }

}
