package com.bdlions.recharge;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.bdlions.recharge.Constants.FIRST_COLUMN;
import static com.bdlions.recharge.Constants.SECOND_COLUMN;
import static com.bdlions.recharge.Constants.THIRD_COLUMN;
import static com.bdlions.recharge.Constants.FOURTH_COLUMN;

/**
 * Created by SampanIT on 21/07/2016.
 */
public class TopUpHistoryListViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String,String>> list;
    Activity activity;

    public TopUpHistoryListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> list){
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 4;
    }

    private class ViewHolder{
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();
        if (convertView == null){
            convertView = inflater.inflate(R.layout.top_up_history_column_row, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.tvFirst);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.tvSecond);
            holder.txtThird = (TextView) convertView.findViewById(R.id.tvThird);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.tvFourth);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String,String> map = list.get(position);
        holder.txtFirst.setText(map.get(FIRST_COLUMN));
        holder.txtSecond.setText(map.get(SECOND_COLUMN));
        holder.txtThird.setText(map.get(THIRD_COLUMN));
        holder.txtFourth.setText(map.get(FOURTH_COLUMN));

        return convertView;
    }
}
