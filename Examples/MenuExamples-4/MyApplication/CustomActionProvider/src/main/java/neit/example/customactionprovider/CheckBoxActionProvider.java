package neit.example.customactionprovider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import neit.example.customactionprovider.R;

public class CheckBoxActionProvider extends ActionProvider implements
        OnCheckedChangeListener {

    /** Context for accessing resources. */
    private final Context mContext;

    public CheckBoxActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
// Inflate the action view to be shown on the action bar.
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.activity_check_box_action_provider,
                null);
        TextView textview = (TextView) view.findViewById(R.id.text);
        textview.setText(getActionText());

        CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(this);

        return view;
    }

    public String getActionText() {
        return "Custom Checkbox";
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkbox:
                Toast.makeText(mContext,
                        isChecked ? "checked" : "unchecked",
                        Toast.LENGTH_SHORT).show();
        }

    }

}
