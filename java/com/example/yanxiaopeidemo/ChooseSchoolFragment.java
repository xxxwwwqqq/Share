package com.example.yanxiaopeidemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.yanxiaopeidemo.adapter.SchoolAdapter;
import com.example.yanxiaopeidemo.entitys.City;
import com.example.yanxiaopeidemo.entitys.Major;
import com.example.yanxiaopeidemo.entitys.School;
import com.example.yanxiaopeidemo.entitys.SchoolInfo;
import com.example.yanxiaopeidemo.entitys.Subject;
import com.example.yanxiaopeidemo.flowlayout.FlowLayout;
import com.example.yanxiaopeidemo.flowlayout.TagAdapter;
import com.example.yanxiaopeidemo.flowlayout.TagFlowLayout;
import com.zj.filters.FiltrateBean;
import com.zj.filters.FlowPopWindow;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.example.yanxiaopeidemo.ChooseSchoolFragment.historyinfo;
import static com.example.yanxiaopeidemo.ChooseSchoolFragment.majs;
import static com.example.yanxiaopeidemo.ChooseSchoolFragment.subs;
import static com.example.yanxiaopeidemo.MainActivity.citylist;
import static com.example.yanxiaopeidemo.MainActivity.schoolInfos;
import static com.example.yanxiaopeidemo.MainActivity.username;
import static com.example.yanxiaopeidemo.SearchActivity.searchlist;

public class ChooseSchoolFragment extends Fragment {
    private View root;
    private ListView listView;
    private ImageButton ibScreen;
    private TextView tvSearch;
    private Button confirm;
    private DrawerLayout drawerLayout;
    private LinearLayout right;
    private GridView loc;
    private GridView major;
    private GridView score;
    public static List<School> schools = new ArrayList<>();
    public static List<Major> majs = new ArrayList<>();//专业信息
    public static List<Subject> subs = new ArrayList<Subject>();// 学科
    public static List<String> historyinfo = new ArrayList<>();//用户历史搜索信息
    //默然展示词条个数
    private final int DEFAULT_RECORD_NUMBER = 10;
    private List<String> recordList = new ArrayList<>();
    private TagAdapter mRecordsAdapter;


    private ImageView clearAllRecords;
    private TagFlowLayout tagFlowLayout;
    private ImageView moreArrow;
    private LinearLayout mHistoryContent;
    private TextView tvHis;


    //筛选框控件
    private FlowPopWindow flowPopWindow;
    //筛选框数据
    private List<FiltrateBean> dictList = new ArrayList<>();
    public static List<String> list = new ArrayList<>();//被选择的条件集合

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.choose_school_fragment, container, false);
        findViews();
        try {
            UpZyInfoThread thread1 = new UpZyInfoThread();
            thread1.start();
            thread1.join();
            DownUserAllHistorySearchInfoThread thread = new DownUserAllHistorySearchInfoThread();
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        getCityInfo();

        Log.i("tttttwwwwwwx", majs.size() + "");
        initData();
        initListView();
        initDaoData();
        initDaoView();
        setListener();
        initParam();
        initView();
        DownUserAllHistorySearchInfoThread thread = new DownUserAllHistorySearchInfoThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return root;
    }

    private void initDaoView() {
        //创建历史标签适配器
        //为标签设置对应的内容
        mRecordsAdapter = new TagAdapter<String>(historyinfo) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tv_history,
                        tagFlowLayout, false);
                //为标签设置对应的内容
                tv.setText(s);
                return tv;
            }
        };


        tagFlowLayout.setAdapter(mRecordsAdapter);


        //view加载完成时回调
        tagFlowLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean isOverFlow = tagFlowLayout.isOverFlow();
                boolean isLimit = tagFlowLayout.isLimit();
                if (isLimit && isOverFlow) {
                    moreArrow.setVisibility(View.VISIBLE);
                } else {
                    moreArrow.setVisibility(View.GONE);
                }
            }
        });

        moreArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagFlowLayout.setLimit(false);
                mRecordsAdapter.notifyDataChanged();
            }
        });

        Log.e("searchlistsize",historyinfo.size()+"");


        //清除所有记录
        clearAllRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("确定要删除全部历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tagFlowLayout.setLimit(true);
                        //清除所有数据
                        historyinfo.clear();
                        initDaoView();
                        DeleteAllHistoryInfoThread thread = new DeleteAllHistoryInfoThread();
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
    private void showDialog(String dialogTitle, @NonNull DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogTitle);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    private void initDaoData() {
        if (null == historyinfo || historyinfo.size() == 0) {
            mHistoryContent.setVisibility(View.GONE);
            tvHis.setVisibility(View.GONE);
            clearAllRecords.setVisibility(View.GONE);

        } else {
            mHistoryContent.setVisibility(View.VISIBLE);
            tvHis.setVisibility(View.VISIBLE);
            clearAllRecords.setVisibility(View.VISIBLE);
        }
        if (mRecordsAdapter != null) {
            mRecordsAdapter.setData(historyinfo);
            mRecordsAdapter.notifyDataChanged();
        }

    }


    private void initView() {
        ibScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flowPopWindow = new FlowPopWindow(getActivity(), dictList);
                flowPopWindow.showAsDropDown(ibScreen);
                flowPopWindow.setOnConfirmClickListener(new FlowPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick() {
                        StringBuilder sb = new StringBuilder();
                        for (FiltrateBean fb : dictList) {
                            List<FiltrateBean.Children> cdList = fb.getChildren();
                            for (int x = 0; x < cdList.size(); x++) {
                                FiltrateBean.Children children = cdList.get(x);
                                if (children.isSelected()) {
                                    sb.append(fb.getTypeName() + ":" + children.getValue() + "；");
                                    list.add(children.getValue());
                                    Log.i("twx", list.get(list.size() - 1));
                                }

                            }
                        }
                        if (!TextUtils.isEmpty(sb.toString()))
                            Toast.makeText(getContext(), "111" + sb.toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), ScreenResultActivity.class);
                        intent.putExtra("cityname", list.get(list.size() - 2));
                        intent.putExtra("zy", list.get(list.size() - 1));
                        Log.e("cityname", list.get(list.size() - 2));
                        Log.e("zy", list.get(list.size() - 1));
                        startActivity(intent);
                    }
                });

            }
        });
    }

    private void initParam() {
        FiltrateBean fb1 = new FiltrateBean();
        fb1.setTypeName("地区");
        List<FiltrateBean.Children> childrenList = new ArrayList<>();
        for (City city : citylist) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(city.getName());
            childrenList.add(cd);
        }
        fb1.setChildren(childrenList);

        FiltrateBean fb2 = new FiltrateBean();
        fb2.setTypeName("专业");
        List<FiltrateBean.Children> childrenList2 = new ArrayList<>();
        for (Major m : majs) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            if (!m.getName().equals("★")) {
                cd.setValue(m.getName());
            } else {
                for (int i = 0; i < subs.size(); i++) {
                    if (subs.get(i).getDm().equals(m.getPredm())) {
                        cd.setValue(subs.get(i).getName());
                    }
                }
            }

            childrenList2.add(cd);
        }
        fb2.setChildren(childrenList2);

        dictList.add(fb1);
        dictList.add(fb2);
    }


    private void setListener() {
        ibScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SearchActivity.class);
                startActivity(i);
            }
        });
    }

    private void initListView() {
        SchoolAdapter adapter = new SchoolAdapter(schools, R.layout.school_list_item, getContext());
        listView.setAdapter(adapter);
    }

    private void initData() {
        if (schools.size() != 0) {
            schools.clear();
        }
        School s1 = new School(R.mipmap.njdx, "南京大学", "位于南京", "985、211高校", "375-380");
        School s2 = new School(R.mipmap.njsfdx, "南京师范大学", "位于南京", "211高校", "355-360");
        School s3 = new School(R.mipmap.bjgydx, "北京工业大学", "位于北京", "211高校", "365-370");
        School s4 = new School(R.mipmap.qhdx, "清华大学", "位于北京", "985、211高校", "385-390");
        schools.add(s1);
        schools.add(s2);
        schools.add(s3);
        schools.add(s4);
    }

    private void findViews() {
        listView = root.findViewById(R.id.school_list);
        ibScreen = root.findViewById(R.id.ib_screen);
        tvSearch = root.findViewById(R.id.tv_search);
        drawerLayout = root.findViewById(R.id.drawer_layout_home);
        score = root.findViewById(R.id.score);
        tagFlowLayout = root.findViewById(R.id.fl_search_records);
        clearAllRecords = root.findViewById(R.id.clear_all_records);
        moreArrow = root.findViewById(R.id.iv_arrow);
        mHistoryContent = root.findViewById(R.id.ll_history_content);
        tvHis = root.findViewById(R.id.tv_his);
    }

}




/**
 * 获取所有专业信息
 */
class UpZyInfoThread extends Thread {
    @Override
    public void run() {
//        Major m2 = new Major("全部", "", "");
//        majs.add(m2);
        if (majs.size() != 0) {
            majs.clear();
        }
        Document document = null;
        try {
            document = Jsoup.connect("http://www.cdgdc.edu.cn/xwyyjsjyxx/sy/glmd/267001.shtml").get();
            Elements elements = document.select("tbody");
            int i = 1;
            while (i < 394) {
                String[] strs = (elements.get(0).children().get(i).children().text()).split("\\s+");
//				System.out.println(Arrays.toString(strs));
                for (int a = 0; a < strs.length; a++) {
                    Matcher m = Pattern.compile("[一-龥]").matcher(strs[a]);
                    if (strs[a].length() == 6 && !m.find()) {
                        String ml_dm = strs[a].substring(0, 4);
                        String name = strs[a + 1];
//						System.out.println(ml_dm);
                        Major m1 = new Major(name, strs[a], ml_dm);
                        majs.add(m1);
                    } else if (strs[a].length() == 4 && !m.find()) {
                        String ml_dm = strs[a].substring(0, 2);
                        String name = strs[a + 1];
                        System.out.println(name);
                        Subject s = new Subject(name, strs[a], ml_dm);
                        subs.add(s);

                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}



/**
 * 接受服务端发送的用户历史搜索信息
 */
class DownUserAllHistorySearchInfoThread extends Thread {
    String s = ConfigUtil.SERVER_ADDR + "DownUserHistorySearchInfoServlet";
    @Override
    public void run() {
        try {
            URL url = new URL(s);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
            String str = reader.readLine();
            if (null!=str){
                String[] strs = str.split("&&&");
                for (int i = 1;i<strs.length;i++){
                    historyinfo.add(strs[i]);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


/**
 * 删除用户所有搜索信息
 */
class DeleteAllHistoryInfoThread extends Thread{
    String s = ConfigUtil.SERVER_ADDR + "DeleteAllHistoryInfoServlet";
    @Override
    public void run() {
        String keyValue = "?username=" + username;
        try {
            URL url = new URL(s+keyValue);
            url.openStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

