package com.example.user.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SubAdapter extends BaseAdapter {
    ArrayList<Sub> list;
    Context context;
    int resource;

    public SubAdapter(ArrayList<Sub> list, Context context, int resource) {
        this.list = list;
        this.context = context;
        this.resource = resource;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int i) {
        return list.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if ( view == null ) {
            LayoutInflater intflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = intflater.inflate(resource, viewGroup, false);
        }

        TextView title = (TextView) view.findViewById(R.id.subcontent_listview);
        TextView number = (TextView) view.findViewById(R.id.subcontent_listview_subscribes);

        title.setText(list.get(i).getId());
        number.setText("[" + list.get(i).getSubscribes().size() + "]");

        return view;
    }
}
