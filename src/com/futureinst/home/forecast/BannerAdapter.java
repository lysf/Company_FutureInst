package com.futureinst.home.forecast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.futureinst.model.homeeventmodel.QueryEventDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2015/10/16.
 */
public class BannerAdapter extends FragmentPagerAdapter{
    private List<QueryEventDAO> events;
    public BannerAdapter(FragmentManager fm) {
        super(fm);
        events = new ArrayList<>();
//        this.events = events;
    }
    public void setList(List<QueryEventDAO> events){
        this.events = events;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
//        return events.size() == 0 ? null :BannerFragment.newInstance(events.get(i%events.size()));
        return BannerFragment.newInstance(events.get(i));
    }


    @Override
    public int getCount() {
//        return events.size() == 0 ? 0 :Integer.MAX_VALUE;
        return events.size();
    }
}
