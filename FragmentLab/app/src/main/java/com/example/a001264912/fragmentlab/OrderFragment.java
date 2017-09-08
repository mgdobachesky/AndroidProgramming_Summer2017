package com.example.a001264912.fragmentlab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    private EditText firstName;
    private EditText lastName;
    private Spinner chocolateType;
    private EditText numBars;
    private RadioGroup shippingGroup;
    private RadioButton shippingMethod;
    private Button saveOrder;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Copy the view
        view = inflater.inflate(R.layout.fragment_order, container, false);

        // Initialize views
        firstName = (EditText)view.findViewById(R.id.txtFirstName);
        lastName = (EditText)view.findViewById(R.id.txtLastName);
        chocolateType = (Spinner)view.findViewById(R.id.spnChocolateType);
        numBars = (EditText)view.findViewById(R.id.txtNumBars);
        shippingGroup = (RadioGroup)view.findViewById(R.id.rdgShipping);
        saveOrder = (Button)view.findViewById(R.id.btnSaveOrder);

        // Create list to hold spinner items
        List<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add("Dark Chocolate");
        spinnerItems.add("Milk Chocolate");
        spinnerItems.add("Light Chocolate");

        // Configure adapter with spinner items list and set the spinner to use it
        ArrayAdapter<String> spinnerItemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerItems);
        spinnerItemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chocolateType.setAdapter(spinnerItemsAdapter);

        // Set action listeners
        saveOrder.setOnClickListener(SubmitClickListener);

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void LoadFragmentData(Order data) {
        // Initialize variables
        int selectedChocolateType = 0;
        int selectedShippingType = 0;

        // Get controls to be set to the values in first order
        firstName = (EditText)view.findViewById(R.id.txtFirstName);
        lastName = (EditText)view.findViewById(R.id.txtLastName);
        chocolateType = (Spinner)view.findViewById(R.id.spnChocolateType);
        numBars = (EditText)view.findViewById(R.id.txtNumBars);
        shippingGroup = (RadioGroup)view.findViewById(R.id.rdgShipping);

        // Create adapter to use in getting first order's selected spinner item
        ArrayAdapter<String> spinnerAdapter = (ArrayAdapter<String>)chocolateType.getAdapter();
        // Get position of the first order's selected spinner item
        selectedChocolateType = spinnerAdapter.getPosition(data.getChocolateType());

        // Get position of selected radio button
        if(data.isExpeditedShipping()) {
            selectedShippingType = R.id.rdoShipExpedited;
        } else if(!data.isExpeditedShipping()) {
            selectedShippingType = R.id.rdoShipNormal;
        }

        // Set the items selected to be that of the first order
        firstName.setText(data.getFirstName());
        lastName.setText(data.getLastName());
        chocolateType.setSelection(selectedChocolateType);
        numBars.setText(Integer.toString(data.getChocolateQuantity()));
        shippingGroup.check(selectedShippingType);
    }

    private View.OnClickListener SubmitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view2) {

            // Create new order to work with
            Order newOrder = new Order();

            // Set the order's properties
            newOrder.setFirstName(firstName.getText().toString());
            newOrder.setLastName(lastName.getText().toString());
            newOrder.setChocolateType(chocolateType.getSelectedItem().toString());
            newOrder.setChocolateQuantity(Integer.parseInt(numBars.getText().toString()));

            int shippingId = shippingGroup.getCheckedRadioButtonId();
            shippingMethod = (RadioButton)view.findViewById(shippingId);
            if(shippingMethod.getText().equals("Expedited")) {
                newOrder.setExpeditedShipping(true);
            } else if(shippingMethod.getText().equals("Normal")) {
                newOrder.setExpeditedShipping(false);
            }

            // Add order
            MainActivity.CandyOrders.add(newOrder);

            // Call event handler
            onButtonPressed();
        }
    };

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
