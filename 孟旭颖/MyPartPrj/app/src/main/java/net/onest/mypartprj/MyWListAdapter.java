package net.onest.mypartprj;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import net.onest.mypartprj.beans.QuestionBank;

import java.util.List;

public class MyWListAdapter extends BaseAdapter {
    private Context myContext;
    private List<QuestionBank> mQList;
    private LayoutInflater layoutinflater;//视图容器，用来导入布局

    static class ViewHolder {
        private TextView tvKemu;
        private TextView tvTiNum;
        private Button btnStartTi;
    }
    /*
     * 实例化Adapter
     */
    public MyWListAdapter(Context context, List<QuestionBank> dataSet)
    {
        this.myContext = context;
        this.mQList = dataSet;
        this.layoutinflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mQList.size();
    }

    @Override
    public Object getItem(int position) {
        return mQList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view;
        if (convertView == null) {
            holder= new ViewHolder();
            view= layoutinflater.inflate(R.layout.item_list_app, null);
            //获取控件
            holder.tvKemu = view.findViewById(R.id.tv_kemu);
            holder.tvTiNum = view.findViewById(R.id.tv_ti_num);
            holder.btnStartTi = view.findViewById(R.id.btn_start_ti);
            view.setTag(holder);
            holder.tvKemu.setText(mQList.get(position).getCourse());
            holder.tvTiNum.setText(mQList.get(position).getNum()+"");
            Log.i("mxy",mQList.get(position).getCourse()+mQList.get(position).getNum());
            holder.btnStartTi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(myContext, WrongTiListActivity.class);
                    intent.putExtra("fenlei",mQList.get(position).getCourse()+"&"+mQList.get(position).getNum());
                    myContext.startActivity(intent);
                }
            });
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
            holder.tvKemu.setText(mQList.get(position).getCourse());
            holder.tvTiNum.setText(mQList.get(position).getNum()+"");
            Log.i("mxy",mQList.get(position).getCourse()+mQList.get(position).getNum());
            holder.btnStartTi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(myContext, WrongTiListActivity.class);
                    myContext.startActivity(intent);
                }
            });
        }


        return view;
    }

}
