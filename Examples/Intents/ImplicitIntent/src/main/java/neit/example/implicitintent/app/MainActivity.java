package neit.example.implicitintent.app;

import android.net.Uri;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.view.View.OnClickListener;


public class MainActivity extends Activity {

    private Button SendEmail;
    private EditText txtURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         txtURL = (EditText) findViewById(R.id.txtURL);

        Button startBrowser = (Button) findViewById(R.id.start_browser);
        startBrowser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(txtURL.getText().toString()));
                startActivity(i);
            }
        });
        Button startPhone = (Button) findViewById(R.id.start_phone);
        startPhone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("tel:9510300000"));
                startActivity(i);
            }
        });

        Button SendEmail = (Button) findViewById(R.id.SendEmail);
        SendEmail.setOnClickListener(SendEmailListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action
        // bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private OnClickListener SendEmailListener = new OnClickListener() {
        public void onClick(View v) {

            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);


            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"bganek@gmail.com", "bganek2@mail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is the test subject");
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "mail content");
            startActivity(Intent.createChooser(emailIntent, "title of dialog"));


        }
    };
}
