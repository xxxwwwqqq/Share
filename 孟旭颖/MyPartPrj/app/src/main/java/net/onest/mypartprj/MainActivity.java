package net.onest.mypartprj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import net.onest.mypartprj.fragment.FirstFragment;
import net.onest.mypartprj.exersise.SecondFragment;
import net.onest.mypartprj.fragment.ThirdFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取FragmentTabHost的引用
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        //初始化 //管理多个Fragment对象的管理器
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        //创建内容页面TabSpec对象
        TabHost.TabSpec tab1 = fragmentTabHost.newTabSpec("first_tab").setIndicator(getTabSpecView("first_tab", "测试", R.drawable.first));
        fragmentTabHost.addTab(tab1, FirstFragment.class, null);

        TabHost.TabSpec tab2 = fragmentTabHost.newTabSpec("second_tab").setIndicator(getTabSpecView("second_tab", "题库", R.drawable.second));
        fragmentTabHost.addTab(tab2, SecondFragment.class, null);

        TabHost.TabSpec tab3 = fragmentTabHost.newTabSpec("third_tab").setIndicator(getTabSpecView("third_tab", "嘻嘻", R.drawable.third));
        fragmentTabHost.addTab(tab3, ThirdFragment.class, null);

        //处理fragmentTabHost的选项切换事件
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //修改图片和文字颜色
                switch (tabId) {
                    case "first_tab":
                        imageViewMap.get("first_tab").setImageResource(R.drawable.first1);
                        imageViewMap.get("second_tab").setImageResource(R.drawable.second);
                        imageViewMap.get("third_tab").setImageResource(R.drawable.third);
                        textViewMap.get("first_tab").setTextColor(getResources().getColor(R.color.colorBlue));
                        textViewMap.get("second_tab").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("third_tab").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "second_tab":
                        imageViewMap.get("second_tab").setImageResource(R.drawable.second1);
                        imageViewMap.get("first_tab").setImageResource(R.drawable.first);
                        imageViewMap.get("third_tab").setImageResource(R.drawable.third);
                        textViewMap.get("second_tab").setTextColor(getResources().getColor(R.color.colorBlue));
                        textViewMap.get("first_tab").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("third_tab").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "third_tab":
                        imageViewMap.get("third_tab").setImageResource(R.drawable.third1);
                        imageViewMap.get("first_tab").setImageResource(R.drawable.first);
                        imageViewMap.get("second_tab").setImageResource(R.drawable.second);
                        textViewMap.get("third_tab").setTextColor(getResources().getColor(R.color.colorBlue));
                        textViewMap.get("second_tab").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("first_tab").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                }
            }
        });

        //设置默认选项卡
        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("first_tab").setImageResource(R.drawable.first);
        textViewMap.get("first_tab").setTextColor(getResources().getColor(R.color.colorBlue));


    }
    public View getTabSpecView(String tag, String title, int drawable){
        View view = getLayoutInflater().inflate(R.layout.tab_spec_layout,null);

        //获取tab_spec_layout布局当中视图控件的引用
        ImageView icon = view.findViewById(R.id.icon);
        icon.setImageResource(drawable);

        //存储ImageView对象
        imageViewMap.put(tag,icon);

        TextView tvtitle = view.findViewById(R.id.title);
        tvtitle.setText(title);
        textViewMap.put(tag,tvtitle);

        return view;
    }
}
