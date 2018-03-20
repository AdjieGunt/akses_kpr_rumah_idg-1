package com.rumahkpr.akses.aksesrumahkpr.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rumahkpr.akses.aksesrumahkpr.R;

import java.util.List;

/**
 * Created by JEMMY CALAK on 3/19/2018.
 */

public class SpinnerAdapter extends ArrayAdapter {
    private Activity context;
    private List<String> data;

    public SpinnerAdapter(Activity context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(data.get(position));

        label.setTextColor(context.getResources().getColor(R.color.darkGray));
        label.setGravity(Gravity.CENTER);
        label.setTextSize(14);
        label.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        label.setSingleLine(true);
        label.setEllipsize(TextUtils.TruncateAt.END);
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label1 = new TextView(context);
        label1.setText(data.get(position));

        label1.setTextSize(14);
        label1.setPadding(15, 10, 0, 10);
        label1.setTextColor(context.getResources().getColor(R.color.darkGray));
        label1.setGravity(Gravity.LEFT);
        label1.setBackgroundColor(context.getResources().getColor(R.color.white));
        return label1;
    }
}
