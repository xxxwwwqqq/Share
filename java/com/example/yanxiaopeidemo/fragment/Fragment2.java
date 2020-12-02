package com.example.yanxiaopeidemo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yanxiaopeidemo.ConfigUtil;
import com.example.yanxiaopeidemo.R;
import com.example.yanxiaopeidemo.adapter.CollectSchoolAdapter;
import com.example.yanxiaopeidemo.adapter.SearchOrScreenAdapter;
import com.example.yanxiaopeidemo.entitys.SchoolInfo;

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

import static com.example.yanxiaopeidemo.MainActivity.collectschools;
import static com.example.yanxiaopeidemo.MainActivity.schoolInfos;


public class Fragment2 extends Fragment {
    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment2, container, false);
        DownUserAllCollectSchoolInfoThread thread = new DownUserAllCollectSchoolInfoThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LinearLayout linear = root.findViewById(R.id.linear);
        Log.e("collectschools",collectschools.size()+"");
        if (collectschools.size()!=0){
            linear.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(getContext(),"您还没有收藏学校，快去看看吧~",Toast.LENGTH_SHORT).show();
        }
        CollectSchoolAdapter adapter = new CollectSchoolAdapter(collectschools,R.layout.school_item,getContext());
        ListView lv = root.findViewById(R.id.lv_collect_schools);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri uri = Uri.parse(collectschools.get(i).getPath());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        return root;

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
