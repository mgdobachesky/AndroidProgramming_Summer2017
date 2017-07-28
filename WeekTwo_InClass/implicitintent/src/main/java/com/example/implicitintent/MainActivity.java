package com.example.implicitintent;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
    private Button listContacts;
    private Button captureImage;
    private Button showLocation;

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

        listContacts = (Button)findViewById(R.id.btnListContacts);
        captureImage = (Button)findViewById(R.id.btnCaptureImage);
        showLocation = (Button)findViewById(R.id.btnShowLocation);

        listContacts.setOnClickListener(listContactsOnClickListener);
        captureImage.setOnClickListener(captureImageOnClickListener);
        showLocation.setOnClickListener(showLocationOnClickListener);
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

    private OnClickListener listContactsOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
            startActivity(i);
        }
    };

    private OnClickListener captureImageOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(i);
        }
    };

    private OnClickListener showLocationOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=1+New+England+Tech+Blvd%2C+MA"));
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }
    };
}
