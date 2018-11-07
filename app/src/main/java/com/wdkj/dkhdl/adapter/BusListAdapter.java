package com.wdkj.dkhdl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.bean.BusDetailData;
import com.wdkj.dkhdl.date.util.DateTimeUtil;
import com.wdkj.dkhdl.utils.Utils;

import java.util.List;

/**
 * 商户列表
 */
public class BusListAdapter extends BaseAdapter{

    private Context context;
    private List<BusDetailData> list;

    public BusListAdapter(Context context, List<BusDetailData> list) {
        this.context = context;
        this.list = list;
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

    private class ViewHolder {
        TextView tvBusName;
        TextView tvCreateTime;
        TextView tvSubmitStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_bus_list_item, null);
            holder = new ViewHolder();

            holder.tvBusName = (TextView) convertView.findViewById(R.id.bus_list_item_tvBusName);
            holder.tvCreateTime = (TextView) convertView.findViewById(R.id.bus_list_item_tvCreateTime);
            holder.tvSubmitStatus = (TextView) convertView.findViewById(R.id.bus_list_item_tvSubmitStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //商户名
        holder.tvBusName.setText(list.get(position).getMerchant_name());

        //创建时间
        Long createTimeStr = list.get(position).getGmt_create();
        String createTime = DateTimeUtil.stampToFormatDate(createTimeStr,"yyyy-MM-dd HH:mm:ss");
        holder.tvCreateTime.setText(createTime);
        //提交状态 0 未提交  1 审核中 2 审核驳回 3 审核通过
        String statusStr = list.get(position).getMerchant_status();
        //timely_sign;//"0" 没审核过时为0，审核过并审核通过时为1，其中二次修改时值不会根据提交状态而改变
        String timely_signStr = list.get(position).getTimely_sign();
        String status = "";
        if(Utils.isNotEmpty(statusStr)){
            if(statusStr.equals("0")){
                status = "未提交";
            }else if(statusStr.equals("1")){
                status = "审核中";
            }else if(statusStr.equals("2")){
                if(Utils.isNotEmpty(timely_signStr)){
                    if(timely_signStr.equals("0")){
                        status = "初次审核驳回";
                    }else{
                        status = "审核驳回";
                    }
                }else{
                    status = "审核驳回";
                }

            }else if(statusStr.equals("3")){
                status = "审核通过";
            }
            setTextViewStyle(statusStr,holder.tvSubmitStatus);
        }
        holder.tvSubmitStatus.setText(status);
        return convertView;
    }


    private void setTextViewStyle(String status,TextView textView){
        if(status.equals("0")){
            textView.setTextColor(context.getResources().getColor(R.color.gray_999));
            textView.setBackground(context.getResources().getDrawable(R.drawable.status_grey_radius5));
        }else if(status.equals("1")){

            textView.setTextColor(context.getResources().getColor(R.color.yellow_e59623));
            textView.setBackground(context.getResources().getDrawable(R.drawable.status_yellow_radius5));
        }else if(status.equals("2")){

            textView.setTextColor(context.getResources().getColor(R.color.red_FF4400));
            textView.setBackground(context.getResources().getDrawable(R.drawable.status_red_radius5));
        }else if(status.equals("3")){

            textView.setTextColor(context.getResources().getColor(R.color.green_008000));
            textView.setBackground(context.getResources().getDrawable(R.drawable.status_green_radius5));
        }
    }

}
