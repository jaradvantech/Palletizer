package com.example.administrator.Palletizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DesignListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Design> designs;

    public DesignListAdapter(Context context, ArrayList<Design> designs) {
        this.mContext = context;
        this.designs = designs;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return designs.size();
    }

    @Override
    public Object getItem(int position) {
        return designs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.design_list_item, parent, false);
        Design mObject = (Design) getItem(position);

        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.design_list_thumbnail);

        //Title
        TextView titleTextView = (TextView) rowView.findViewById(R.id.design_list_title);
        titleTextView.setText(mObject.getName());

        //Set subtitle and thumbnail according to design type
        String subtitle = "";
        //Verified design
        if(mObject.getType() == 0) {
            subtitle += "Design By LOVALIVE";
            thumbnailImageView.setImageResource(R.mipmap.verified);

         //Custom design
        } else if (mObject.getType() == 1) {
            subtitle += "Custom design";
            //No thumbnail
        }
        subtitle += ", " + mObject.boxList.size() + " boxes.";

        //Subtitle
        TextView subTitleTextView = (TextView) rowView.findViewById(R.id.design_list_subtitle);
        subTitleTextView.setText(subtitle);

        return rowView;
    }

}
