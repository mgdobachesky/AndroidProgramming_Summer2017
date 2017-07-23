package bruce.AdvancedAndroid.togglebutton;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import static bruce.AdvancedAndroid.togglebutton.R.id.switch1;

public class MainActivity extends Activity {


	private ToggleButton bottomToggleButton;
	private TextView results;
	private Switch mySwitch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		bottomToggleButton = (ToggleButton)findViewById(R.id.bottomToggleButton);
		mySwitch = (Switch)findViewById(switch1);
		results = (TextView)findViewById(R.id.results);
		bottomToggleButton.setOnClickListener(bottomToggleButtonChangeListener);
//		mySwitch.setOnClickListener(switchClickChangeListener);
        mySwitch.setOnCheckedChangeListener(switchOnCheckedChangeListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private OnClickListener bottomToggleButtonChangeListener = new OnClickListener() {
		public void onClick(View v) {
			if (bottomToggleButton.isChecked())
			{
				results.setText("Display is on");
			}
			
			else
			{
				results.setText("Display is off");
			}
			}
			
	};

/*	private OnClickListener switchClickChangeListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			if (mySwitch.isChecked())
			{
				results.setText(mySwitch.getText().toString());
			}

			else
			{
				results.setText(mySwitch.getText().toString());
			}
		}
	};
*/
	private OnCheckedChangeListener switchOnCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b)
            {
                results.setText(mySwitch.getTextOn().toString());
            }

            else
            {
                results.setText(mySwitch.getTextOff().toString());
            }
        }
    };
}
