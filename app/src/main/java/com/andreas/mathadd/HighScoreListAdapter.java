package com.andreas.mathadd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoreListAdapter extends ArrayAdapter<HighScoreItem> {
    private Context mContext;
    private int mResource;

    public HighScoreListAdapter(Context context, int resource, ArrayList<HighScoreItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String time = getItem(position).getTime();

        HighScoreItem item = new HighScoreItem(name, time);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(this.mResource, parent, false);

        TextView nameView = (TextView) convertView.findViewById(R.id.listViewName);
        TextView timeView = (TextView) convertView.findViewById(R.id.listViewTime);

        nameView.setText(name);
        timeView.setText(time);

        return convertView;
    }

}
