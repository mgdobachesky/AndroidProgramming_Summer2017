package com.example.calculator;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMIFragment extends Fragment {
	private static float UNDERWEIGHT = 18.5F;
	private static float NORMAL = 24.9F;
	private static float OVERWEIGHT = 29.9F;

	private EditText heightEditText;
	private EditText weightEditText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    
	    View root = null;
        // Only load data into the fragment if it has a parent
        if (container != null) {
            root = inflater.inflate(R.layout.fragment_bmi, container, false);
            
            heightEditText = (EditText) root.findViewById(R.id.height);
            weightEditText = (EditText) root.findViewById(R.id.weight);

            Button calculateButton = (Button) root.findViewById(
                    R.id.bmiButton);
            calculateButton.setOnClickListener(new BMIListener());
        }
		return root;
	}

	class BMIListener implements OnClickListener {
		public void onClick(View v) {
			Resources res = getResources();
			TextView bmiResult = (TextView) getActivity().findViewById(
					R.id.bmiResult);

			try {
				float height = Float.parseFloat(heightEditText.getText()
						.toString());
				float weight = Float.parseFloat(weightEditText.getText()
						.toString());

				float result = (weight / (height * height)) * 703;

				String resultLabel = res.getString(R.string.bmiResultLabel2);

				String classification = "";
				if (result < UNDERWEIGHT) {
					classification = res.getString(R.string.bmiUnderweight);
				} 
				else if (result < NORMAL) {
					classification = res.getString(R.string.bmiNormal);
				} 
				else if (result < OVERWEIGHT) {
					classification = res.getString(R.string.bmiOverweight);
				} 
				else {
					classification = res.getString(R.string.bmiObese);
				}

				bmiResult.setText(String.format(resultLabel, result,
						classification));
			} 
			catch (NumberFormatException e) {
				bmiResult.setText(R.string.bmiResultLabel);
				System.err.println(e);
			}

		}
	}
}
