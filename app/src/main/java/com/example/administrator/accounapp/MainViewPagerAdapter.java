package com.example.administrator.accounapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "MainViewPagerAdapter";
    LinkedList<MainFragment> fragments = new LinkedList<>();
    LinkedList<String> dates = new LinkedList<>();

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }
    //初始化Fragment并添加到链表
    private void initFragments(){
        dates = GlobalUtil.getInstance().databaseHelper.getAvaliableDate();

        if(!dates.contains(DateUtil.getFormattedDate())){
            dates.addLast(DateUtil.getFormattedDate());
        }

        for (String date:dates){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);
        }
    }
    public void reload(){
        //每个fragment都刷新一遍
        for (MainFragment fragment:fragments){
            fragment.reload();
        }
    }

    public int getLastIndex(){
        return fragments.size() - 1;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public int getTotalCost(int index){

        return fragments.get(index).getTotalCost();
    }

    public String getDateStr(int index){
        return dates.get(index);
    }



}
