package com.example.administrator.Palletizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BoxDesignListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<BoxPrototype> mDataSource;
    private final float SCALE = 0.5f;

    public BoxDesignListAdapter(Context context, ArrayList<BoxPrototype> items) {
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
        View rowView = mInflater.inflate(R.layout.editor_list_item, parent, false);


        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.editor_list_image);

        BoxPrototype mObject = (BoxPrototype) getItem(position);
        thumbnailImageView.setImageResource(mObject.textureResource);

        return rowView;
    }

}
