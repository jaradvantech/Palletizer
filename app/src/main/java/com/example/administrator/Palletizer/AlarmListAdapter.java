package com.example.administrator.Palletizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<AlarmObject> mDataSource;

    public AlarmListAdapter(Context context, ArrayList<AlarmObject> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.alarm_list_item, parent, false);

        // Get title element
        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.alarm_list_title);

        // Get subtitle element
        TextView subtitleTextView =
                (TextView) rowView.findViewById(R.id.alarm_list_subtitle);

        // Get detail element
        TextView detailTextView =
                (TextView) rowView.findViewById(R.id.alarm_list_detail);

        // Get thumbnail element
        ImageView thumbnailImageView =
                (ImageView) rowView.findViewById(R.id.alarm_list_thumbnail);

        // 1
        AlarmObject mAlarm = (AlarmObject) getItem(position);


        titleTextView.setText(mContext.getString(R.string.Type_)+ " "  + mAlarm.getType());
        subtitleTextView.setText(mContext.getString(R.string.Source_) + " "  + mAlarm.getSource());
        detailTextView.setText(mAlarm.getTime());
        thumbnailImageView.setImageResource(R.mipmap.alarm);

        return rowView;
    }

}
