package com.wdkj.dkhdl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.bean.BackListData;
import com.wdkj.dkhdl.bean.MerchanIdTypeData;

import java.util.List;

public class SpinnerMerchantIdTypeAdapter extends BaseAdapter{

    private Context context;
    private List<MerchanIdTypeData> list;
    private LayoutInflater inflater;

    public SpinnerMerchantIdTypeAdapter(Context context, List<MerchanIdTypeData> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        private TextView tvName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.spinner_item_layout, null);
            holder.tvName=(TextView) convertView.findViewById(R.id.spinner_item_name);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(list.get(position).getType());
        return convertView;
    }
}
