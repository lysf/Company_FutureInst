package com.futureinst.model.usermodel;

import com.futureinst.model.basemodel.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hao on 2015/11/9.
 */
public class UserTagRecordInfoDAO extends BaseModel implements Serializable {
    private List<UserTagRecordDAO> tag_records;

    public List<UserTagRecordDAO> getTag_records() {
        return tag_records;
    }

    public void setTag_records(List<UserTagRecordDAO> tag_records) {
        this.tag_records = tag_records;
    }

    @Override
    public String toString() {
        return "UserTagRecordInfoDAO{" +
                "tag_records=" + tag_records +
                '}';
    }
}
