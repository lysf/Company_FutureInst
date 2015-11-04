package com.futureinst.model.news;

import com.futureinst.model.basemodel.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hao on 2015/11/1.
 */
public class NewsInfoDAO extends BaseModel implements Serializable {
    private List<NewsDAO> newss;

    public List<NewsDAO> getNewss() {
        return newss;
    }

    public void setNewss(List<NewsDAO> newss) {
        this.newss = newss;
    }

    @Override
    public String toString() {
        return "NewsInfoDAO{" +
                "newss=" + newss +
                '}';
    }
}
