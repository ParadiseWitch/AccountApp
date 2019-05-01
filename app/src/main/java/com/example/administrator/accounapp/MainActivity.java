package com.example.administrator.accounapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";
    private ViewPager viewPager;
    private TickerView amountText;
    private MainViewPagerAdapter pagerAdapter;
    private TextView dateText;
    private int currentPaggerPostion = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据库
        GlobalUtil.getInstance().setContext(getApplicationContext());
        GlobalUtil.getInstance().mainactivity = this;
        //高度为0，取消阴影
        getSupportActionBar().setElevation(0);

        amountText = (TickerView) findViewById(R.id.amount_text);
        amountText.setCharacterLists(TickerUtils.provideNumberList());
        dateText = (TextView) findViewById(R.id.date_text);


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        //提醒有数据变更
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);
        //最后一页是当前页
        viewPager.setCurrentItem(pagerAdapter.getLastIndex());

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                startActivityForResult(intent,1);
            }
        });

        viewPager.setOnPageChangeListener(this);


        amountText.setText(String.valueOf(pagerAdapter.getTotalCost(pagerAdapter.getCount()-1)));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pagerAdapter.reload();
        uptateHeader();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        currentPaggerPostion = i;
        uptateHeader();
    }
    public void uptateHeader(){
        String amount = String.valueOf(pagerAdapter.getTotalCost(currentPaggerPostion));
        amountText.setText(amount);
        String date = pagerAdapter.getDateStr(currentPaggerPostion);
        dateText.setText(DateUtil.getWeekDay(date));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
