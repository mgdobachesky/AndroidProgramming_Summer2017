package neit.example.ListViewExample;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SimpleListViewExample extends ListActivity {

    ListView listview;
    String[] countries = new String[] {

            "Samsung",
            "Nokia",
            "Blackberry",
            "LG",
            "Motorola",
            "Apple",
            "Karbon",
            "Micromax",
            "Zen",
    };

    /** Called when the activity is first created. */

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listview=getListView();

        // Declaring ArrayAdapter for the default listview
        ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,countries);

        // Setting ArrayAdapter for the default listview

        listview.setAdapter(listadapter);

        // Defining ItemClick event Listener

        //       OnItemClickListener listener = new OnItemClickListener() {

        // This method will be triggered when an item in the listview is clicked ( or touched )

        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "You selected : " + countries[i], Toast.LENGTH_SHORT).show();
            }

        });

        // Setting an ItemClickEvent Listener for the listview
        // In this example we are making use the default listview
        //     getListView().setOnItemClickListener(listener);

    }

}

