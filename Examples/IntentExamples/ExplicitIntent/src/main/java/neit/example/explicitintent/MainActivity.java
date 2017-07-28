package neit.example.explicitintent;

import android.net.Uri;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // First intent to use ACTION_VIEW action with correct data
        Button startBrowser_a = (Button) findViewById(R.id.start_browser_a);
        startBrowser_a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://www.example.com"));
                startActivity(i);
            }
        });

        // Second intent to use LAUNCH action with correct data
        Button startBrowser_b = (Button) findViewById(R.id.start_browser_b);
        startBrowser_b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent("com.example.intentdemo.LAUNCH",
                        Uri.parse("http://www.example.com"));
                startActivity(i);
            }
        });

        // Third intent to use LAUNCH action with incorrect data
        // will blow up because it is looking for an http url in the filter
        Button startBrowser_c = (Button) findViewById(R.id.start_browser_c);
        startBrowser_c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent("com.example.intentdemo.LAUNCH",
                        Uri.parse("https://www.example.com"));
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the
        // action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
