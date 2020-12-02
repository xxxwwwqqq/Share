package com.example.yanxiaopeidemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yanxiaopeidemo.adapter.SearchOrScreenAdapter;
import com.example.yanxiaopeidemo.entitys.SchoolInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.yanxiaopeidemo.ChooseSchoolFragment.majs;
import static com.example.yanxiaopeidemo.MainActivity.citylist;
import static com.example.yanxiaopeidemo.MainActivity.schoolInfos;
import static com.example.yanxiaopeidemo.ScreenResultActivity.dm;
import static com.example.yanxiaopeidemo.ScreenResultActivity.id;
import static com.example.yanxiaopeidemo.ScreenResultActivity.results;
import static com.example.yanxiaopeidemo.ScreenResultActivity.zy;

public class ScreenResultActivity extends AppCompatActivity {
    private ListView listView;
    public static int id = 0;
    public static String dm = null;
    public static String zy = null;
    public static String name = null;
    public static List<SchoolInfo> schools = new ArrayList<>();
    public static List<SchoolInfo> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_result);
        listView = findViewById(R.id.main_lv_screen_results);
        final Intent intent = getIntent();
        name = intent.getStringExtra("cityname");
        zy = intent.getStringExtra("zy");

        Log.i("schoolname","school" + name);
        for (int i = 0;i<citylist.size();i++){
            if (citylist.get(i).getName().equals(name)){
                id = citylist.get(i).getId();
            }
        }
        for (int j = 0;j<majs.size();j++){
            if (majs.get(j).getName().equals(zy)){
                dm = majs.get(j).getDm();
            }
        }
        ScreenThread thread = new ScreenThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("results.size",results.size()+"");
        if (results.size()==0){
            TextView tvv = findViewById(R.id.tvv);
            tvv.setText("暂时没有符合条件的院校~");
        }
        SearchOrScreenAdapter adapter = new SearchOrScreenAdapter(results,R.layout.school_item,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = "https://yz.chsi.com.cn/sch/";
                Uri uri = Uri.parse(path);
                Intent intent1 = new Intent(Intent.ACTION_VIEW,uri);
//                intent.putExtra(SearchManager.QUERY,results.get(position).getName());
//                intent.setAction(Intent.ACTION_WEB_SEARCH);
                startActivity(intent1);
            }
        });


    }
}


/**
 * 获取筛选后的数据
 */
class ScreenThread extends Thread {
    @Override
    public void run() {
        Integer start = 0;
        try {
            String url ="";
            if (id==0){
                url = "https://yz.chsi.com.cn/zyk/specialityDetail.do?zymc=" + zy +"&zydm=" + dm + "&ccdm=&cckey=10&ssdm=&method=distribution#zyk-zyfb";
                Log.e("url",url);
                Document document = Jsoup.connect(url).get();
                Elements es = document.select("li[title]");
                String[] strs = es.text().split("\\s+");
                if (results.size()!=0){
                    results.clear();
                }
                for (int i = 0;i<strs.length;i++){
                    for (int j = 0;j<schoolInfos.size();j++){
                        if (schoolInfos.get(j).getName().equals(strs[i])){
                            results.add(schoolInfos.get(j));
                        }
                    }

                }
            }else if(id!=0){
                url = "https://yz.chsi.com.cn/zyk/specialityDetail.do?zymc=" + zy +"&zydm=" + dm + "&ccdm=&cckey=10&ssdm=" + id + "&method=distribution#zyk-zyfb";
                Log.e("url",url);
                Document document = Jsoup.connect(url).get();
                Elements es = document.select("li[title]");
                String[] strs = es.text().split("\\s+");
                if (results.size()!=0){
                    results.clear();
                }
                for (int i = 0;i<strs.length;i++){
                    for (int j = 0;j<schoolInfos.size();j++){
                        if (schoolInfos.get(j).getName().equals(strs[i])){
                            results.add(schoolInfos.get(j));
                        }
                    }
                }
            }
//            else if(id!=0 && dm.equals("")){
//                for (int i = 0;i<schoolInfos.size();i++){
//                    if (schoolInfos.get(i).getName().equals(name)){
//                        results.add(schoolInfos.get(i));
//                    }
//                }
//
//            }else if(id == 0 && dm.equals("")){
//                results.addAll(schoolInfos);
//            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
