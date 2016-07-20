package com.example.sampanit.recharge;

/**
 * Created by SampanIT on 13/06/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomGrid extends BaseAdapter{
    private Context mContext;

    List<String> web = new ArrayList<String>();
    List<Integer> Imageids = new ArrayList<Integer>();
   // private final int[] Imageid;

    public CustomGrid(Context c,List<String> web,List<Integer> Imageid ) {
        mContext = c;
        this.Imageids = Imageid;
        this.web = web;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.recharge_services, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(web.get(position));
            imageView.setImageResource(Imageids.get(position));
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
