package bruce.android.spinner;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Spinner topSpinner;
	private Spinner bottomSpinner;
	private Button SubmitButton;
	private TextView results;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		topSpinner = (Spinner)findViewById(R.id.topSpinner);
		bottomSpinner = (Spinner)findViewById(R.id.bottomSpinner);
		SubmitButton = (Button)findViewById(R.id.btnSubmit);
		results = (TextView)findViewById(R.id.results);
		
		List<String> list = new ArrayList<String>();
		list.add("Item 1");
		list.add("Item 2");
		list.add("Item 3");
		list.add("Item 4");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bottomSpinner.setAdapter(dataAdapter);
		
		SubmitButton.setOnClickListener(SubmitButtonClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private OnClickListener SubmitButtonClickListener = new OnClickListener() {
		public void onClick(View v) {
				results.setText("The flavor is : " + topSpinner.getSelectedItem().toString());
		}		
	};
	
}
