package com.example.yanxiaopeidemo.menu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yanxiaopeidemo.R;
import com.dalong.marqueeview.MarqueeView;

public class PersonalWish extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_wish_layout);
        MarqueeView marqueeView = findViewById(R.id.tv_marquee);
        marqueeView.setFocusable(true);
        marqueeView.requestFocus();
        marqueeView.setText("我使劲跑");//设置文本
        marqueeView.startScroll(); //开始

//        //数据集合
//        List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
//        //获取SimpleMF 跑马灯工厂
//        SimpleMF<String> marqueeFactory = new SimpleMF(getActivity());
//        //添加数据到工厂
//        marqueeFactory.setData(datas);
//        //MarqueeView设置工厂
//        simpleMarqueeView1.setMarqueeFactory(marqueeFactory);
//        //开启跑马灯
//        simpleMarqueeView1.startFlipping();
    }
}
