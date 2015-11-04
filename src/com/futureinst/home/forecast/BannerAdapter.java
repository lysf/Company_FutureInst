package com.futureinst.home.forecast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.futureinst.model.homeeventmodel.QueryEventDAO;

import java.util.List;

/**
 * Created by hao on 2015/10/16.
 */
public class BannerAdapter extends FragmentPagerAdapter{
    private List<QueryEventDAO> events;
    public BannerAdapter(FragmentManager fm,List<QueryEventDAO> events) {
        super(fm);
        this.events = events;
    }

    @Override
    public Fragment getItem(int i) {
        return BannerFragment.newInstance(events.get(i));
    }

    @Override
    public int getCount() {
        return events.size();
    }
}
