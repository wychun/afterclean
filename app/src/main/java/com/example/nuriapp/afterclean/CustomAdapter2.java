package com.example.nuriapp.afterclean;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter2 extends BaseAdapter{
    String[] date, timesize, help, price;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;

    public CustomAdapter2(Activity activity, String[] optionlist1, String[] optionlist2, String[] optionlist3, String[] optionlist4,int[] images) {
        date=optionlist1;
        timesize=optionlist2;
        help=optionlist3;
        price=optionlist4;
        context=activity;
        imageId=images;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return date.length;
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
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;

        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item2, null);
        holder.textView1 = (TextView)rowView.findViewById(R.id.sub08_text02);
        holder.textView2 = (TextView)rowView.findViewById(R.id.sub08_text03);
        holder.textView3 = (TextView)rowView.findViewById(R.id.sub08_text04);
        holder.textView4 = (TextView)rowView.findViewById(R.id.sub08_text05);
        holder.imageView = (ImageView)rowView.findViewById(R.id.sub08_help_img);
        holder.textView1.setText(date[position]);
        holder.textView2.setText(timesize[position]);
        holder.textView3.setText(help[position]);
        holder.textView4.setText(price[position]);
        holder.imageView.setImageResource(imageId[position]);

        return rowView;
    }
}
