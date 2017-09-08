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

public class LoanFragment extends Fragment {
    private EditText principalEditText;
    private EditText termEditText;
    private EditText interestEditText;

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = null;
        // Only load data into the fragment if it has a parent
        if (container != null) {
            root = inflater.inflate(R.layout.fragment_loan, container,
                    false);

            principalEditText = (EditText) root
                    .findViewById(R.id.principalAmount);
            termEditText = (EditText) root.findViewById(R.id.term);
            interestEditText = (EditText) root
                    .findViewById(R.id.interest);

            Button calculateButton = (Button) root
                    .findViewById(R.id.loanButton);
            calculateButton.setOnClickListener(new LoanListener());
        }
        return root;
    }

    class LoanListener implements OnClickListener {
        public void onClick(View v) {
            Resources res = getResources();
            TextView loanResult = (TextView) getActivity()
                    .findViewById(R.id.loanResult);

            try {
                float principal = Float.parseFloat(principalEditText
                        .getText().toString());
                int months = Integer.parseInt(termEditText.getText()
                        .toString());
                float interest = Float.parseFloat(interestEditText
                        .getText().toString());
                float rate = interest / 1200;

                double result = (rate + (rate / (Math.pow(1 + rate,
                        months) - 1))) * principal;

                String resultLabel = res
                        .getString(R.string.loanResultLabel2);
                loanResult.setText(String.format(resultLabel,
                        principal, interest, months, result));
            }
            catch (NumberFormatException e) {
                loanResult.setText(R.string.loanResultLabel);
                System.err.println(e);
            }

        }
    }
}
