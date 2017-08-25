package com.example.a001264912.weeksix_inclass;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import static android.R.attr.id;
import static android.R.attr.onClick;
import static com.example.a001264912.weeksix_inclass.R.id.lsResults;
import static com.example.a001264912.weeksix_inclass.R.id.txtID;
import static com.example.a001264912.weeksix_inclass.R.id.txtName;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    ListView lstResults;
    TextView txtId;
    TextView txtName;
    TextView txtType;
    TextView txtCount;
    Button btnAdd;
    Button btnUpdate;
    Button btnGetAll;
    Button btnGetByName;
    Button btnGetCount;

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

        String dbPath = this.getDatabasePath("accountsManager").toString();

        lstResults = (ListView)findViewById(R.id.lsResults);
        txtCount = (TextView)findViewById(R.id.tvResults);
        txtId = (TextView)findViewById(R.id.txtID);
        txtName = (TextView)findViewById(R.id.txtName);
        txtType = (TextView)findViewById(R.id.txtType);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnGetAll = (Button)findViewById(R.id.btnGetAllAccounts);
        btnGetByName = (Button)findViewById(R.id.btnGetAccountsByName);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnGetCount = (Button)findViewById(R.id.btnGetCountOfAccounts);
        db = new DatabaseHandler(this);

        btnAdd.setOnClickListener(addAccount);
        btnGetAll.setOnClickListener(getAllAccounts);
        btnGetByName.setOnClickListener(getAccountByName);
        btnGetCount.setOnClickListener(getAccountsCount);
        btnUpdate.setOnClickListener(updateAccount);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public View.OnClickListener updateAccount = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setID(Integer.parseInt(txtId.getText().toString()));
            bankAccount.setName(txtName.getText().toString());
            bankAccount.setType(txtType.getText().toString());
            db.updateAccount(bankAccount);
        }
    };

    public View.OnClickListener getAllAccounts = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            List<BankAccount> myAccounts = db.getAllAccounts();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);

            // adding entries in List
            for (int counter = 0 ; counter < myAccounts.size();counter++) {
                adapter.add(myAccounts.get(counter).getID() + " - " +
                        myAccounts.get(counter).getName() + " - " +
                        myAccounts.get(counter).getType());
            }


            lstResults.setAdapter(adapter);
        }
    };

    public View.OnClickListener getAccountByName = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BankAccount myAccount = db.getAccount(txtName.getText().toString());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);

            // adding entries in List
            adapter.add(myAccount.getID() + " - " + myAccount.getName() + " - " + myAccount.getType());

            lstResults.setAdapter(adapter);
        }
    };

    public View.OnClickListener addAccount = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setName(txtName.getText().toString());
            bankAccount.setType(txtType.getText().toString());
            db.addAccount(bankAccount);
        }
    };

    public View.OnClickListener getAccountsCount = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String countAccounts = Integer.toString(db.getAccountsCount());
            txtCount.setText(countAccounts);
        }
    };

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
