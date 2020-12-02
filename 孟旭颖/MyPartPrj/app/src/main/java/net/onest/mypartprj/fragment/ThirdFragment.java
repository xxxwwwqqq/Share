package net.onest.mypartprj.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.onest.mypartprj.R;


public class ThirdFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //加载内容页面的布局文件（将内容页面的XML布局文件转成View类型的对象）
        view = inflater.inflate(R.layout.fragment_layout,container,false);

        //获取内容页面当中空间的引用
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("组合贷款");
        Button btnReg = view.findViewById(R.id.btn_count);
        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                EditText etnum1 = v.findViewById(R.id.et_num1);
                EditText etnum2 = v.findViewById(R.id.et_num2);
                EditText etnum3 = v.findViewById(R.id.et_num3);
                String num1 = etnum1.getText().toString();
                int num11 = Integer.parseInt(num1);
                String num2 = etnum1.getText().toString();
                int num12 = Integer.parseInt(num2);
                String num3 = etnum1.getText().toString();
                int num13 = Integer.parseInt(num3);
                double result = num11*num12*num13;

                Log.e("结果：", ""+result);

            }
        });

        return view;
    }
}
