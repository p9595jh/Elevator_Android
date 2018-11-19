package com.example.user.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SuggestAdapter extends BaseAdapter {
    ArrayList<Suggest> list;
    Context context;
    int resource;

    public SuggestAdapter(ArrayList<Suggest> list, Context context, int resource) {
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

    public  View getView(int i, View view, ViewGroup viewGroup) {
        if ( view == null ) {
            LayoutInflater intflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = intflater.inflate(resource, viewGroup, false);
        }

        ImageView image = (ImageView) view.findViewById(R.id.suggest_view_image);
        TextView id = (TextView) view.findViewById(R.id.suggest_view_id);
        TextView date = (TextView) view.findViewById(R.id.suggest_view_date);
        TextView text = (TextView) view.findViewById(R.id.suggest_view_text);

        image.setImageResource(list.get(i).getImag());
        Uri uri = Uri.parse(MainActivity.SERVER_ADDRESS + "/images/profileimages/" + list.get(i).getId());
        image.setImageURI(uri);
        id.setText(list.get(i).getNickname());
        date.setText(list.get(i).getWritedata());
        text.setText(list.get(i).getComment());

        return view;
    }
}
