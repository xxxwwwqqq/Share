package net.onest.mypartprj.exersise;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import net.onest.mypartprj.MyQAdapter;
import net.onest.mypartprj.R;
import net.onest.mypartprj.beans.QuestionBank;

import java.util.ArrayList;
import java.util.List;

public class FragmentOne extends Fragment {
    private View view;
    private SwipeMenuListView swipeMenuListView;
    private List<QuestionBank> mQList;
    private MyQAdapter myQAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.fragment1,container,false);
        //获取控件
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(view.getContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        swipeMenuListView = view.findViewById(R.id.swipelistView);
        swipeMenuListView.setMenuCreator(creator);

        /*
        设置监听
         */
        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                QuestionBank q = mQList.get(position);
                switch (index){
                    case 0:
//                        delete(q);数据库中删除
                        mQList.remove(position);
                        myQAdapter.notifyDataSetChanged();
                        break;
                }
                return true;
            }
        });
        // 监测用户在ListView的SwipeMenu侧滑事件
        swipeMenuListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                Log.d("位置:" + position, "开始侧滑...");
            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
        //设置一些死数据
        mQList = new ArrayList<>();
        QuestionBank q1 = new QuestionBank("中国近代史",3,1);
        QuestionBank q2 = new QuestionBank("马克思主义理论",2,1);
        QuestionBank q3 = new QuestionBank("毛泽东思想",2,1);
        QuestionBank q4 = new QuestionBank("邓小平理论",6,1);
        QuestionBank q5 = new QuestionBank("新社会主义",2,1);
        mQList.add(q1);
        mQList.add(q2);
        mQList.add(q3);
        mQList.add(q4);
        mQList.add(q5);

        //绑定Adapter
        myQAdapter = new MyQAdapter(view.getContext(),mQList);
        swipeMenuListView.setAdapter(myQAdapter);


//        btnJindaishi = view.findViewById(R.id.btn_jindaishi);
//        btnJindaishi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(view.getContext(), ExerciseActivity.class);
//                view.getContext().startActivity(intent);
//            }
//        });

        return view;
    }

    public int dp2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
