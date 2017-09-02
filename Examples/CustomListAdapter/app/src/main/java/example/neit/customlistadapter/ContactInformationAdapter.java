package example.neit.customlistadapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import example.neit.customlistadapter.R;

/**
 * Created by BGANEK on 5/5/2016.
 */
public class ContactInformationAdapter extends ArrayAdapter<ContactInformation> {
    Context context;
    int layoutResourceId;
    ArrayList<ContactInformation> data = null;

    public ContactInformationAdapter(Context context, int layoutResourceId, ArrayList<ContactInformation> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemInformationHolder holder = null;

        if(row == null)
        {

            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ItemInformationHolder();
            holder.txtName = (TextView)row.findViewById(R.id.tvName);
            holder.txtAddress = (TextView)row.findViewById(R.id.tvAddress);
            holder.txtCity = (TextView)row.findViewById(R.id.tvCity);
            holder.txtState = (TextView)row.findViewById(R.id.tvState);
            holder.txtZip = (TextView)row.findViewById(R.id.tvZip);

            row.setTag(holder);
        }
        else
        {
            holder = (ItemInformationHolder) row.getTag();
        }

        //ItemInformation myItem = data[position];
        ContactInformation myItem = data.get(position);
        holder.txtName.setText(myItem.Name);
        holder.txtAddress.setText(myItem.Address);
        holder.txtCity.setText(myItem.City);
        holder.txtState.setText(myItem.State);
        holder.txtZip.setText(myItem.Zip);

        return row;
    }

    static class ItemInformationHolder
    {
        TextView txtName;
        TextView txtAddress;
        TextView txtCity;
        TextView txtState;
        TextView txtZip;

    }


}
