package com.example.calculator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

public class CalculatorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // if we are in landscape mode, then this activity
            // is not necessary
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // first time - use the extras to determine which
            // fragment to display within this activity
            Bundle extras = getIntent().getExtras();
            Calculator calculatorType = (Calculator) extras
                    .getSerializable("calculatorType");
            Fragment fragment = calculatorType.getFragment();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(android.R.id.content, fragment);
            ft.commit();
        }
    }
}
