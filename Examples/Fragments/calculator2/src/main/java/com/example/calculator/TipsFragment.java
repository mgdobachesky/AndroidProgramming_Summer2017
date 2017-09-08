package com.example.calculator;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class TipsFragment extends Fragment {
    private EditText billAmount;
    private RadioGroup rg;

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = null;
        // Only load data into the fragment if it has a parent
        if (container != null) {

            root = inflater.inflate(R.layout.fragment_tips, container,
                    false);

            billAmount = (EditText) root.findViewById(R.id.billAmount);
            billAmount.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    updateTextViews();
                }

                public void beforeTextChanged(CharSequence s,
                        int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                        int before, int count) {
                }
            });

            rg = (RadioGroup) root.findViewById(R.id.tipRadioGroup);
            rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group,
                        int checkedId) {
                    updateTextViews();
                }
            });

        }
        return root;
    }

    private void updateTextViews() {
        Resources res = getResources();
        TextView tipAmountTextView = (TextView) getActivity()
                .findViewById(R.id.tipAmount);
        TextView totalBillTextView = (TextView) getActivity()
                .findViewById(R.id.totalBill);
        RadioButton rb = (RadioButton) getActivity().findViewById(
                rg.getCheckedRadioButtonId());

        try {
            float amount = Float.parseFloat(billAmount.getText()
                    .toString());
            // Use split function to remove % sign from String
            float tipPercent = Float.parseFloat(rb.getText()
                    .toString().split("%")[0]);
            tipPercent = tipPercent / 100;

            float tip = amount * tipPercent;
            float total = amount + tip;

            String tipLabel = res
                    .getString(R.string.tip_amount_label2);
            String totalLabel = res
                    .getString(R.string.total_bill_label2);
            tipAmountTextView.setText(String.format(tipLabel, tip));
            totalBillTextView
                    .setText(String.format(totalLabel, total));
        }
        catch (NumberFormatException e) {
            tipAmountTextView.setText(R.string.tip_amount_label);
            totalBillTextView.setText(R.string.total_bill_label);
            System.err.println(e);
        }
    }
}
