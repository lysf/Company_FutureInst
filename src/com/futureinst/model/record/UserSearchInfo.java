package com.futureinst.model.record;

import com.futureinst.model.basemodel.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hao on 2015/12/16.
 */
public class UserSearchInfo extends BaseModel implements Serializable{
    private List<UserRecordDAO> userRecords;

    public List<UserRecordDAO> getUserRecords() {
        return userRecords;
    }

    public void setUserRecords(List<UserRecordDAO> userRecords) {
        this.userRecords = userRecords;
    }

    @Override
    public String toString() {
        return "UserSearchInfo{" +
                "userRecords=" + userRecords +
                '}';
    }
}
