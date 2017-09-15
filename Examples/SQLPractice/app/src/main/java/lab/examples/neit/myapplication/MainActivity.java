package lab.examples.neit.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amitshekhar.DebugDB;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    EditText ID;
    EditText Name;
    EditText Type;
    Button AddAccount;
    Button UpdateAccount;
    Button GetAllAccounts;
    Button GetAccountByName;
    Button GetCountOfAccounts;
    TextView Results;
    ListView lsResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ID = (EditText)findViewById(R.id.txtID);
        Name= (EditText)findViewById(R.id.txtName);
        Type = (EditText)findViewById(R.id.txtType);

        AddAccount = (Button)findViewById(R.id.btnAdd);
        UpdateAccount = (Button)findViewById(R.id.btnUpdate);
        GetAllAccounts = (Button)findViewById(R.id.btnGetAllAccounts);
        GetAccountByName = (Button)findViewById(R.id.btnGetAccountsByName);
        GetCountOfAccounts = (Button)findViewById(R.id.btnGetCountOfAccounts);

        Results = (TextView)findViewById(R.id.tvResults);
        lsResults = (ListView)findViewById(R.id.lsResults);
        ID.setText(DebugDB.getAddressLog());

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

    public void AddAccount(View myView){
        DatabaseHandler myDb = new DatabaseHandler(MainActivity.this);
        int id;
        String name;
        String type;

        id = 0;
        name = Name.getText().toString();
        type = Type.getText().toString();
        BankAccount myAccount = new BankAccount(id, name, type);

        myDb.addContact(myAccount);


    }


    public void GetAllAccounts(View myView){
        DatabaseHandler myDb = new DatabaseHandler(MainActivity.this);
        String nameToSearch = Name.getText().toString();

        List<BankAccount> myAccounts = myDb.getAllAccounts();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        // adding entries in List

        for (int counter = 0 ; counter < myAccounts.size();counter++) {
            adapter.add(myAccounts.get(counter).getID() + " - " +
                    myAccounts.get(counter).getName() + " - " +
                    myAccounts.get(counter).getType());
        }
        // setting adapter to list
        lsResults.setAdapter(adapter);
    }

    public void GetAccountByName(View myView){
        DatabaseHandler myDb = new DatabaseHandler(MainActivity.this);
        String nameToSearch = Name.getText().toString();

        List<BankAccount> myAccounts = myDb.getBankAccountByName(nameToSearch);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        // adding entries in List

        for (int counter = 0 ; counter < myAccounts.size();counter++) {
            adapter.add(myAccounts.get(counter).getID() + " - " +
                    myAccounts.get(counter).getType());
        }
        // setting adapter to list
        lsResults.setAdapter(adapter);


    }
    public void GetCountOfAccounts(View myView){
        DatabaseHandler myDb = new DatabaseHandler(MainActivity.this);
        int NumberOfAccounts;

        NumberOfAccounts = myDb.getAccountsCount();

        Results.setText("Number Of Accounts = " + Integer.toString(NumberOfAccounts));


    }

}
