package example.neit.customlistadapter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        final ArrayList<ContactInformation> myContactList = new ArrayList<ContactInformation>();

        ContactInformation Contact1 = new ContactInformation("Bruce Ganek","101 Main Street","Lincoln","RI","02829");
        myContactList.add(Contact1);
        ContactInformation Contact2 = new ContactInformation("Mickey Mouse","Disneyland","Orlando","FL","12829");
        myContactList.add(Contact2);
        ContactInformation Contact3 = new ContactInformation("Sam Spade","10 Freemont Street","Hollywood","CA","22829");
        myContactList.add(Contact3);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        ContactInformationAdapter adapter = new ContactInformationAdapter(getApplicationContext(),
                R.layout.item_listview_row, myContactList);


        ListView listView1 = (ListView)findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
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
