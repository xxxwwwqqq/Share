package net.onest.mypartprj;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import net.onest.mypartprj.beans.SingleChoice;
import net.onest.mypartprj.utils.ScaleAnimatorUtils;

import java.util.ArrayList;
import java.util.List;

public class WrongTiActivity extends Activity {
    private ViewPager mViewPager;
    private int mPosition;
    private String currentOpt;
    private TextView tvStem;
    private TextView tvOptA;
    private TextView tvOptB;
    private TextView tvOptC;
    private TextView tvOptD;
    private TextView tvAnTitle;
    private TextView tvAnalysis;
    private LinearLayout llOptA;
    private LinearLayout llOptB;
    private LinearLayout llOptC;
    private LinearLayout llOptD;
    private ImageView ivMore;
    private ImageView ivOpt1;
    private ImageView ivOpt2;
    private ImageView ivOpt3;
    private ImageView ivOpt4;
    private ImageView ivShoucang;
    private ImageView ivDelete;
    private LinearLayout llShoucang;
    private LinearLayout llDelete;
    private List<SingleChoice> singleChoices;
    private List<View> myViewList;
    private CommonPopupWindow window;
    private TextView tvShoucang;
    private TextView tvDelete;
    private CommonPopupWindow.LayoutGravity layoutGravity;
    private int keyNum = 0;
    private boolean keyNumA = false;
    private boolean keyNumB = false;
    private boolean keyNumC = false;
    private boolean keyNumD = false;
    private MyPager myPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exercise);

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
        mViewPager = findViewById(R.id.in_viewpager);
        myViewList = new ArrayList<>();
        LayoutInflater layoutInflater = getLayoutInflater().from(WrongTiActivity.this);
        for(int j = 0;j<singleChoices.size();j++){
            View view1 = layoutInflater.inflate(R.layout.exercise_first, null,false);
            myViewList.add(view1);
        }
        myPager = new MyPager(myViewList);
        mViewPager.setAdapter(myPager);
        mPosition = mViewPager.getCurrentItem();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("PageChange-Select", "position:" + position);
                mPosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.i("PageChange-State", "state:SCROLL_STATE_IDLE(滑动闲置或滑动结束)");


                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        Log.i("PageChange-State", "state:SCROLL_STATE_DRAGGING(手势滑动中)");
                        Log.i("当前页",""+mPosition);

                        if(mPosition<singleChoices.size()-1){
                            View view = myViewList.get(mPosition+1);
                            initView(view,singleChoices.get(mPosition+1));
                            ivShoucang.setImageResource(R.drawable.shoucang);
                            ivDelete.setImageResource(R.drawable.delete);
                            keyNum=0;
                            keyNumA=false;
                            keyNumB=false;
                            keyNumC=false;
                            keyNumD=false;
                            setViewListener();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        Log.i("PageChange-State", "state:SCROLL_STATE_SETTLING(代码执行滑动中)");
                        break;
                    default:
                        break;
                }

            }
        });
        View view = myViewList.get(mPosition);
        initView(view,singleChoices.get(mPosition));
        setViewListener();

        //更多（包含收藏和加入错题集）
        window = new CommonPopupWindow(this,R.layout.popup_gravity_wrong,500, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view=getContentView();
                tvDelete = view.findViewById(R.id.tv_delete);
                tvShoucang = view.findViewById(R.id.tv_shoucang);
                ivShoucang = view.findViewById(R.id.iv_shoucang);
                ivDelete = view.findViewById(R.id.iv_delete);
                llDelete = view.findViewById(R.id.ll_delete);
                llShoucang = view.findViewById(R.id.ll_shoucang);
                ivShoucang.setSelected(false);
                ivDelete.setSelected(false);
                llShoucang.setOnClickListener(new MyListener());
                llDelete.setOnClickListener(new MyListener());
            }

            @Override
            protected void initEvent() {

            }
        };

        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.CENTER_HORI);

    }
    private void setViewListener() {
        MyListener listener = new MyListener();
        llOptA.setOnClickListener(listener);
        llOptB.setOnClickListener(listener);
        llOptC.setOnClickListener(listener);
        llOptD.setOnClickListener(listener);
        ivMore.setOnClickListener(listener);
    }

    private void initView(View view,SingleChoice singleChoice) {
        tvStem = view.findViewById(R.id.tv_stem);
        tvOptA = view.findViewById(R.id.tv_optA);
        tvOptB = view.findViewById(R.id.tv_optB);
        tvOptC = view.findViewById(R.id.tv_optC);
        tvOptD = view.findViewById(R.id.tv_optD);
        llOptA = view.findViewById(R.id.ll_optA);
        llOptB = view.findViewById(R.id.ll_optB);
        llOptC = view.findViewById(R.id.ll_optC);
        llOptD = view.findViewById(R.id.ll_optD);
        ivMore = findViewById(R.id.iv_more);
        ivOpt1 = view.findViewById(R.id.iv_opt1);
        ivOpt2 = view.findViewById(R.id.iv_opt2);
        ivOpt3 = view.findViewById(R.id.iv_opt3);
        ivOpt4 = view.findViewById(R.id.iv_opt4);
//        ivCuoti = view.findViewById(R.id.iv_cuoti);
//        ivShoucang = view.findViewById(R.id.iv_shoucang);
        tvAnTitle = view.findViewById(R.id.tv_antitle);
        tvAnalysis = view.findViewById(R.id.tv_analysis);
        currentOpt = singleChoice.getCorrect();
        tvStem.setText(singleChoice.getStem());
        tvOptA.setText(singleChoice.getOptionA());
        tvOptB.setText(singleChoice.getOptionB());
        tvOptC.setText(singleChoice.getOptionC());
        tvOptD.setText(singleChoice.getOptionD());

    }

    class MyListener implements View.OnClickListener{

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_optA:
                    if(keyNumA==false){
                        keyNumA=true;
                        tvOptA.setTextColor(WrongTiActivity.this.getResources().getColor(R.color.colorSel));
                        keyNum++;
                    }else{
                        keyNumA=false;
                        tvOptA.setTextColor(WrongTiActivity.this.getResources().getColor(R.color.colorNoSel));
                        keyNum--;
                    }
                    if(keyNum==singleChoices.get(mPosition).getKeyNum()){
                        Log.i("mxy","点击完毕A");
                        judgmentOpt();
                    }
                    Log.i("mxy","点击了A");
                    break;
                case R.id.ll_optB:
                    if(keyNumB==false){
                        keyNumB=true;
                        tvOptB.setTextColor(WrongTiActivity.this.getResources().getColor(R.color.colorSel));
                        keyNum++;
                    }else{
                        keyNumB=false;
                        tvOptB.setTextColor(WrongTiActivity.this.getResources().getColor(R.color.colorNoSel));
                        keyNum--;
                    }
                    if(keyNum==singleChoices.get(mPosition).getKeyNum()){
                        Log.i("mxy","点击完毕B");
                        judgmentOpt();
                    }
                    break;
                case R.id.ll_optC:
                    if(keyNumC==false){
                        keyNumC=true;
                        tvOptC.setTextColor(WrongTiActivity.this.getResources().getColor(R.color.colorSel));
                        keyNum++;
                    }else{
                        keyNumC=false;
                        tvOptC.setTextColor(WrongTiActivity.this.getResources().getColor(R.color.colorNoSel));
                        keyNum--;
                    }
                    if(keyNum==singleChoices.get(mPosition).getKeyNum()){
                        Log.i("mxy","点击完毕C");
                        judgmentOpt();
                    }
                    break;
                case R.id.ll_optD:
                    if(keyNumD==false){
                        keyNumD=true;
                        tvOptD.setTextColor(WrongTiActivity.this.getResources().getColor(R.color.colorSel));
                        keyNum++;
                    }else{
                        keyNumD=false;
                        tvOptD.setTextColor(WrongTiActivity.this.getResources().getColor(R.color.colorNoSel));
                        keyNum--;
                    }
                    if(keyNum==singleChoices.get(mPosition).getKeyNum()){
                        Log.i("mxy","点击完毕D");
                        judgmentOpt();
                    }
                    break;
                case R.id.iv_more:
                    window.showBashOfAnchor(ivMore,layoutGravity,0,0);
                    break;
                case R.id.ll_shoucang:
                    if(ivShoucang.isSelected()==false){
                        ivShoucang.setImageResource(R.drawable.shoucangok);
                        ivShoucang.setSelected(true);
                        ScaleAnimatorUtils.setScalse(ivShoucang);
                        Toast.makeText(WrongTiActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    }else {
                        ivShoucang.setImageResource(R.drawable.shoucang);
                        ivShoucang.setSelected(false);
                        Toast.makeText(WrongTiActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                    }

                    Log.i("mxy收藏posion",""+mPosition);

                    break;
                case R.id.ll_delete:
                    if(ivDelete.isSelected()==false){
                        ivDelete.setImageResource(R.drawable.deleteok);
                        ivDelete.setSelected(true);
                        ScaleAnimatorUtils.setScalse(ivDelete);
                        delPage();
                        Toast.makeText(WrongTiActivity.this,"移出成功",Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    }

    private void delPage() {
        int position = mViewPager.getCurrentItem();//获取当前页面位置
        myViewList.remove(position);//删除一项数据源中的数据
        myPager.notifyDataSetChanged();//通知UI更新
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void judgmentOpt() {
        switch (currentOpt){
            case "A":
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "B":
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "C":
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "D":
                Log.i("mxy","正确答案是D");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "AB":
                Log.i("mxy","正确答案是AB");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "AC":
                Log.i("mxy","正确答案是AC");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "AD":
                Log.i("mxy","正确答案是AD");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "ABC":
                Log.i("mxy","正确答案是ABC");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "ABD":
                Log.i("mxy","正确答案是ABD");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "ABCD":
                Log.i("mxy","正确答案是ABCD");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "BC":
                Log.i("mxy","正确答案是BC");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "BD":
                Log.i("mxy","正确答案是BD");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "BCD":
                Log.i("mxy","正确答案是BCD");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
            case "CD":
                Log.i("mxy","正确答案是CD");
                ivOpt1.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt2.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.no,null));
                ivOpt3.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                ivOpt4.setImageDrawable(WrongTiActivity.this.getResources().getDrawable(R.drawable.yes,null));
                tvAnTitle.setText("解析：");
                tvAnalysis.setText(singleChoices.get(mPosition).getAnalysis());
                break;
        }

    }

}
