package com.example.anurag.conducive;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private Context context;
    private int resource;
    public int textview;
    public CustomAdapter(Context context, int resource,int textview, List objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.inflater = LayoutInflater.from( context );
        this.context=context;
        this.textview = textview;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        convertView=(LinearLayout)inflater.inflate(resource,null);
        Action action= (Action) getItem(position);
        TextView txtName = (TextView) convertView.findViewById(textview);
        txtName.setText(action.getActionName());
        Log.d("TEXT",action.getActionName());
//        ImageView img=(ImageView) convertView.findViewById(R.id.imageView1);
//        img.setImageDrawable(action.getIcon());
        return convertView;

    }
}
