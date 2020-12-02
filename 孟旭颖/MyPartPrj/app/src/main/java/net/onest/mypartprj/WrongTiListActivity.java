package net.onest.mypartprj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import net.onest.mypartprj.beans.SingleChoice;

import java.util.ArrayList;
import java.util.List;

public class WrongTiListActivity extends Activity {
    private List<SingleChoice> singleChoices;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exersize_wrongti);

        Intent result = getIntent();
        String fenlei = result.getStringExtra("fenlei");
        Log.i("跳转练习",fenlei);
        String[] arr = fenlei.split("&");
        int num = Integer.parseInt(arr[1]);
        singleChoices = new ArrayList<>();
        for(int i = 0;i<num;i++){
            SingleChoice s = new SingleChoice("蔡徐坤的应援色是()"+i,"红色"+i,"蓝色"+i,"粉色"+i,"金色"+i,"D",
                    "掉落人间 你像丘比特赐予我的首选 靠在枕边 ah光绕过你天使般的脸 ah这感觉实在太危险 能否再对我温柔一点点 不忍心再带你去冒险,掉落人间 你像丘比特赐予我的首选 靠在枕边 ah光绕过你天使般的脸 ah这感觉实在太危险 能否再对我温柔一点点 不忍心再带你去冒险,掉落人间 你像丘比特赐予我的首选 靠在枕边 ah光绕过你天使般的脸 ah这感觉实在太危险 " +
                            "能否再对我温柔一点点 不忍心再带你去冒险掉落人间 你像丘比特赐予我的首选 靠在枕边 ah光绕过你天使般的脸 ah这感觉实在太危险 能否再对我温柔一点点 不忍心再带你去冒险",2);
            singleChoices.add(s);
        }



    }
}
