package com.example.rafik.store;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rafik on 3/21/2017.
 */


public class simplefragmentpageAdapter extends FragmentPagerAdapter {

    private ArrayList<String>Tabdata =new ArrayList<String>();

    String[] tb = new String[Tabdata.size()];
    List<String> temp = new ArrayList<String>(Arrays.asList("1", "12","11","23","25"));
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private String [] tabtitle=new String[]{"tab1","tab2","tab3","tab4","tab5","tab6","tab7","tab8","tab9","tab10"};
    Context context;
    private int pagecount=Tabdata.size();
    public simplefragmentpageAdapter(FragmentManager fm ) {
        super(fm);


    }






    @Override
    public Fragment getItem(int position) {

        switch (position)
        {

            default:  fragmentDemo fragmentDemo1 = new fragmentDemo();

                Bundle b = new Bundle();
                int p=position+1;
                b.putString("position",String.valueOf(p));

                fragmentDemo1.setArguments(b);
                return fragmentDemo1;


        }    }

    @Override
    public int getCount() {
        return mFragmentTitleList.size();
    }


    public void addFragment( String title) {
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mFragmentTitleList.get(position);

    }

}
