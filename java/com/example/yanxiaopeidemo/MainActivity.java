package com.example.yanxiaopeidemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yanxiaopeidemo.entitys.City;
import com.example.yanxiaopeidemo.entitys.SchoolInfo;
import com.example.yanxiaopeidemo.fragment.MusicFragment;
import com.example.yanxiaopeidemo.fragment.PlanFragment;
import com.example.yanxiaopeidemo.fragment.MainFragment;
import com.example.yanxiaopeidemo.fragment.WorkFragment;
import com.example.yanxiaopeidemo.menu.PersonalCollection;
import com.example.yanxiaopeidemo.menu.PersonalDecoration;
import com.example.yanxiaopeidemo.menu.PersonalWhiteList;
import com.example.yanxiaopeidemo.menu.PersonalWish;
import com.example.yanxiaopeidemo.view.CircleImageView;
import com.example.yanxiaopeidemo.view.FragmentTabHost;
import com.example.yanxiaopeidemo.view.MenuItemView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static com.example.yanxiaopeidemo.MainActivity.citylist;
import static com.example.yanxiaopeidemo.MainActivity.collectschools;
import static com.example.yanxiaopeidemo.MainActivity.schoolInfos;
import static com.example.yanxiaopeidemo.MainActivity.username;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private DrawerLayout mDrawer;
    private FragmentTabHost mTabHost;
    private FrameLayout mTabContent;
    private View mTabView;

    private String[] mTabTexts;

    private int[] mTabIcons = new int[]{
            R.mipmap.home,
            R.mipmap.school,
            R.mipmap.plan,
            R.mipmap.work,
            R.mipmap.music};
    private int[] mTabBacks = new int[]{
            R.drawable.selector_nvg_home,
            R.drawable.selector_nvg_school,
            R.drawable.selector_nvg_plan,
            R.drawable.selector_nvg_work,
            R.drawable.selector_nvg_music
    };

    private TextView mTvTitle;
    private ImageView mIvAdd;
    private TextView mTvAdd;
    private TextView mTvMore;
    private RelativeLayout mRlTitle;
    private RelativeLayout mRlMenu;
    private ColorShades mColorShades;
    private LinearLayout mLlContentMain;
    private CircleImageView mCivHead;
    private TextView mTvMessgeCount;
    private TextView mTvContactsCount;
    private TextView mTvStarCount;
    private MenuItemView menuDecoration;
    private MenuItemView menuCollect;
    private MenuItemView menuWhiteList;
    private MenuItemView menuWall;

    public static List<City> citylist = new ArrayList<>();//省市信息
    public static List<SchoolInfo> schoolInfos = new ArrayList<>();//学校信息
    public static List<SchoolInfo> collectschools = new ArrayList<>();//用户收藏的学校信息
    public static String username = "twx";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setListener();
        try {
            UpUserNameThread thread = new UpUserNameThread();
            thread.start();
            thread.join();
            NewThread thread3 = new NewThread();
            thread3.start();
            thread3.join();
            SearchThread thread2 = new SearchThread();
            thread2.start();
            DownUserAllCollectSchoolInfoThread thread1 = new DownUserAllCollectSchoolInfoThread();
            thread1.start();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        MyClickListener myClickListener = new MyClickListener();
        menuDecoration.setOnClickListener(myClickListener);
        menuCollect.setOnClickListener(myClickListener);
        menuWhiteList.setOnClickListener(myClickListener);
        menuWall.setOnClickListener(myClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        mDrawer = findViewById(R.id.drawer_layout);
        mRlMenu = findViewById(R.id.rl_menu);
        mLlContentMain = findViewById(R.id.ll_content_main);
        mRlTitle = findViewById(R.id.rl_title);
        mCivHead = findViewById(R.id.civ_head);
        mTvTitle = findViewById(R.id.tv_title);
        menuWhiteList = findViewById(R.id.menu_white_list);
        menuDecoration = findViewById(R.id.menu_decoration);
        menuCollect = findViewById(R.id.menu_collect);
        menuWall = findViewById(R.id.menu_wish);
        //底部导航设置
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //关联主布局
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //默认设置选中第一个
        mTabHost.setCurrentTab(0);
        //去掉在版本中的横线,FragmentTabHost在低版本中,每个Tab之间会有条横线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        initBottomNavigationView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initBottomNavigationView() {
        mTabTexts = getResources().getStringArray(R.array.tab_texts);

        for (int i = 0; i < mTabTexts.length; i++) {
            mTabView = View.inflate(this, R.layout.layout_item_tab, null);
            ((TextView) mTabView.findViewById(R.id.tv_tab_text)).setText(mTabTexts[i]);
            ((ImageView) mTabView.findViewById(R.id.iv_tab_icon)).setImageResource(mTabBacks[i]);

            //创建TabSpec
            TabHost.TabSpec messageTabSpec = mTabHost.newTabSpec(mTabTexts[i]).setIndicator(mTabView);

            Bundle bundle = new Bundle();
            bundle.putString(TAG, mTabTexts[i]);
            switch (i) {
                case 0:
                    mTabHost.addTab(messageTabSpec, MainFragment.class, bundle);
                    break;
                case 1:
                    mTabHost.addTab(messageTabSpec, ChooseSchoolFragment.class, bundle);
                    break;
                case 2:
                    mTabHost.addTab(messageTabSpec, PlanFragment.class, bundle);
                    break;
                case 3:
                    mTabHost.addTab(messageTabSpec, WorkFragment.class, bundle);
                    break;
                case 4:
                    mTabHost.addTab(messageTabSpec, MusicFragment.class, bundle);
                    break;
            }
        }
    }


    private void initEvent() {
        mCivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.openDrawer(GravityCompat.START);
                }
            }
        });

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i(TAG, "onTabChanged: tabId -- " + tabId);
                mTvTitle.setText(tabId);
            }
        });

        mColorShades = new ColorShades();

        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //设置主布局随菜单滑动而滑动
                int drawerViewWidth = drawerView.getWidth();
                mLlContentMain.setTranslationX(drawerViewWidth * slideOffset);

                //设置控件最先出现的位置
                double padingLeft = drawerViewWidth * (1 - 0.618) * (1 - slideOffset);
                mRlMenu.setPadding((int) padingLeft, 0, 0, 0);

                //设置Title颜色渐变
//                mColorShades.setFromColor("#001AA7F2")
//                        .setToColor(Color.WHITE)
//                        .setShade(slideOffset);
//                mRlTitle.setBackgroundColor(mColorShades.generate());
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.menu_decoration:
                    Toast.makeText(MainActivity.this, "个性装扮", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, PersonalDecoration.class);
                    startActivity(intent);
                    break;
                case R.id.menu_collect:
                    Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(MainActivity.this, PersonalCollection.class);
                    startActivity(intent2);
                    break;
                case R.id.menu_white_list:
                    Toast.makeText(MainActivity.this, "白名单", Toast.LENGTH_LONG).show();
                    Intent intent3 = new Intent(MainActivity.this, PersonalWhiteList.class);
                    startActivity(intent3);
                    break;
                case R.id.menu_wish:
                    Toast.makeText(MainActivity.this, "心愿单", Toast.LENGTH_LONG).show();
                    Intent intent4 = new Intent(MainActivity.this, PersonalWish.class);
                    startActivity(intent4);
                    break;
            }
        }
    }
}

/**
 * 获取所有省市信息
 */
class NewThread extends Thread {
    @Override
    public void run() {
        Document document;
        City c = new City(0, "全国");
        citylist.add(c);
        {
            try {
                //获取所有省市
                document = Jsoup.connect("https://yz.chsi.com.cn/sch/search.do?ssdm=&yxls=").get();
                Element content = document.getElementById("ssdm");
                Elements cities = content.getElementsByTag("option");
                for (Element city : cities) {
                    if (!city.attr("value").equals("")) {
                        int value = Integer.parseInt(city.attr("value"));
                        String name = city.text();
                        City c1 = new City(value, name);
                        citylist.add(c1);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


/**
 * 获取所有学校信息
 */
class SearchThread extends Thread {
    @Override
    public void run() {
        if (schoolInfos.size() != 0) {
            schoolInfos.clear();
        }
        Document document;
        Integer start = 0;
        int a = 0;
        while (a < 43) {
            String url = "https://yz.chsi.com.cn/sch/search.do?start=" + start;
            try {
                document = Jsoup.connect(url).get();
                Elements elements = document.select("tbody tr");
                for (Element element : elements) {
                    String path = "https://yz.chsi.com.cn/" + element.attr("href");
                    Elements es = element.select("td:lt(3)");
                    String[] strs = es.text().split("\\s+");
                    SchoolInfo s = new SchoolInfo(strs[0], strs[1], strs[2], path);
                    schoolInfos.add(s);
                }
                start += 20;
                a++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


/**
 * 向客户端发送当前登录的用户名
 */
class UpUserNameThread extends Thread {
    String s = ConfigUtil.SERVER_ADDR + "UpUserNameServlet";

    @Override
    public void run() {
        String keyValue = "?name=" + username;
        try {
            URL url = new URL(s + keyValue);
            url.openStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 接收服务端发送的用户收藏的所有学校信息
 */
class DownUserAllCollectSchoolInfoThread extends Thread {
    String s = ConfigUtil.SERVER_ADDR + "DownUserAllCollectSchoolInfoServlet";

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(s);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, "utf-8"));
            String str = reader.readLine();
            str = new String(str.getBytes(), "utf-8");
            Log.e("str",str);
            reader.close();
            in.close();
            if (null != str){
                if (collectschools.size()!=0){
                    collectschools.clear();
                }
                String[] strs = str.split("&&&");
                for (int i = 1; i < strs.length; i=i+4) {
                    SchoolInfo s = new SchoolInfo(strs[i],strs[i+1],strs[i+2],strs[i+3]);
                    collectschools.add(s);
                    Log.e("collectschools", collectschools.size()+"");
                }
            }
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        } catch (
                UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}




