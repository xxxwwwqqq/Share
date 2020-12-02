package net.onest.mypartprj.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import net.onest.mypartprj.DepthPageTransformer;
import net.onest.mypartprj.MyPager;
import net.onest.mypartprj.R;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {
    private View view;
    private ViewPager viewPager;

    private List<View> myViewList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //加载内容页面的布局文件（将内容页面的XML布局文件转成View类型的对象）

        view = inflater.inflate(R.layout.fragment_exercise,container,false);

        //获取内容页面当中空间的引用
        viewPager = view.findViewById(R.id.in_viewpager);
        myViewList = new ArrayList<>();
        View view1 = inflater.inflate(R.layout.exercise_first,null);
        View view2 = inflater.inflate(R.layout.exercise_second,null);
        myViewList.add(view1);
        myViewList.add(view2);
        viewPager.setAdapter(new MyPager(myViewList));
        viewPager.setPageTransformer(true,new DepthPageTransformer());

        return view;
    }
}
