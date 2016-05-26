package com.example.nuriapp.afterclean;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
    String[] result;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Activity activity, String[] optionlist, int[] images) {
        result=optionlist;
        context=activity;
        imageId=images;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item, null);
        holder.textView = (TextView)rowView.findViewById(R.id.item_text);
        holder.imageView = (ImageView)rowView.findViewById(R.id.item_image);
        holder.textView.setText(result[position]);
        holder.imageView.setImageResource(imageId[position]);

        return rowView;
    }
}
