package bruce.AdvancedAndroid;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class TableAndSliderActivity extends Activity {
	

	private TextView result;
	private Button addOperation;
	private EditText number1;
	private EditText number2;
	private SeekBar seekNumber2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState == null)
        {
        }
        else
        {
        }
        
        result = (TextView)findViewById (R.id.result);
        addOperation = (Button)findViewById(R.id.addOperation);
        number1 = (EditText)findViewById(R.id.Number1);
        number2 = (EditText)findViewById(R.id.Number2);
        seekNumber2 = (SeekBar)findViewById(R.id.seekNumber2);
        
        addOperation.setOnClickListener(ResultClickListener); 
        seekNumber2.setOnSeekBarChangeListener(seekNumber2ChangeListener);
        
    }
    
	private OnClickListener ResultClickListener = new OnClickListener() {
		public void onClick(View v) {
	String firstNumber = number1.getText().toString();
	String secondNumber = number2.getText().toString();

	float resultText;

	resultText = Float.valueOf(firstNumber).floatValue() + 
			Float.valueOf(secondNumber).floatValue();
	result.setText(Float.toString(resultText));
		}
	};
	
	
	private OnSeekBarChangeListener seekNumber2ChangeListener = new OnSeekBarChangeListener(){
			public void onProgressChanged(SeekBar mySeekBar, int progress, boolean fromUser) {
				CalculateResultFromSlider(mySeekBar.getProgress());
				number2.setText(Integer.toString(progress));
				
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
	
	};
	
	private void CalculateResultFromSlider(int sliderValue){
		String firstNumber = number1.getText().toString();

		float resultText;

		resultText = Float.valueOf(firstNumber).floatValue() + 
				Float.valueOf(sliderValue);
		result.setText(Float.toString(resultText));
	};
		
}
	




