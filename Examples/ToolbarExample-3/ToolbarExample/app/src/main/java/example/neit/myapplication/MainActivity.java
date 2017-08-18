package example.neit.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.backspacefilled);
        toolbar.setNavigationContentDescription(getResources().getString(R.string.backspaceFilled));
        toolbar.setLogo(R.drawable.usmusic);
        toolbar.setLogoDescription(R.string.logo);

        Toolbar toolbarBottom = (Toolbar)findViewById(R.id.toolbarBottom);
        toolbarBottom.setLogo(R.drawable.backspacefilled);
        toolbarBottom.setTitle("A cool test");
        toolbarBottom.setSubtitle("The Subtitle test");
        toolbarBottom.setNavigationIcon(R.drawable.asparagus);
        toolbarBottom.setLogo(R.drawable.frenchmusic);
        toolbarBottom.inflateMenu(R.menu.menu_toolbar);
        toolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_FirstOption)
                    Toast.makeText(getApplicationContext(), "First Option Picked", Toast.LENGTH_LONG).show();

                if (id == R.id.action_SecondOption)
                    Toast.makeText(getApplicationContext(), "Second Option Picked", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        toolbarBottom.setNavigationOnClickListener(new View.OnClickListener(){

               @Override
               public void onClick(View v) {
                   Toast.makeText(MainActivity.this,"Navigation Button Pressed", Toast.LENGTH_LONG).show();
                }
           }
        );

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
