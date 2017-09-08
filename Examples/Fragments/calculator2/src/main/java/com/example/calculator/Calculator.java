package com.example.calculator;

import android.app.Fragment;

public enum Calculator {
    TIPS, BMI, LOAN;

    public Fragment getFragment() {
        switch (this) {
        case TIPS:
            return new TipsFragment();
        case LOAN:
            return new LoanFragment();
        default:
            return new BMIFragment();
        }
    }
}
