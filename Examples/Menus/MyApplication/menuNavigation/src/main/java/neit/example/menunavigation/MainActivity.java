package neit.example.menunavigation;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_search:
                // search action
                LocationFound();
                return true;
            case R.id.action_label:
                // location found
                return true;
            case R.id.action_play:
                // refresh
                return true;
            case R.id.action_record:
                // help action
                return true;
            case R.id.action_save:
                // check for updates action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void LocationFound() {
        Intent i = new Intent(MainActivity.this, higherLevelLocationActivity.class);
        startActivity(i);
    }
}
