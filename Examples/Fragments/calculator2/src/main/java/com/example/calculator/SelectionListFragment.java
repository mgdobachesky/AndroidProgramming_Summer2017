package com.example.calculator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectionListFragment extends ListFragment {
    private boolean bothFragmentsDisplayed = false;
    private Calculator currentlySelectedFragment;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Populate list with our calculator options
        String[] listItems = getResources().getStringArray(
                R.array.listItems);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                listItems);
        setListAdapter(adapter);

        // Determine if we are displaying both fragments simultaneously
        View otherFragment = getActivity().findViewById(
                R.id.placeholder);
        if (otherFragment != null
                && otherFragment.getVisibility() == View.VISIBLE) {
            bothFragmentsDisplayed = true;
        }

        if (bothFragmentsDisplayed) {
            if (savedInstanceState != null) {
                showCalculator((Calculator) savedInstanceState
                        .getSerializable("currentFragment"));
            }
            else {
                showCalculator(Calculator.TIPS);
            }
            getListView().setItemChecked(0, true);
        }
    }

    @Override
    public void onListItemClick(ListView lv, View v, int position,
            long id) {
        String chosenItem = lv.getItemAtPosition(position).toString();
        showCalculator(Calculator.valueOf(chosenItem.toUpperCase()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("currentFragment",
                currentlySelectedFragment);
    }

    public void showCalculator(Calculator fragmentToDisplay) {
        if (fragmentToDisplay == null) {
            fragmentToDisplay = Calculator.TIPS;
        }
        if (bothFragmentsDisplayed) {
            if (!fragmentToDisplay.equals(currentlySelectedFragment)) {
                Fragment fragment = fragmentToDisplay.getFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.placeholder, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
        else {
            // only displaying one fragment
            Intent i = new Intent();
            i.setClass(getActivity(), CalculatorActivity.class);
            i.putExtra("calculatorType", fragmentToDisplay);
            startActivity(i);
        }
        currentlySelectedFragment = fragmentToDisplay;
    }

}
