package com.futureinst.todaytask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.TaskType;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.dailytask.DailyTaskInfoDAO;
import com.futureinst.model.dailytask.DailyTaskMapInfoDAO;
import com.futureinst.model.dailytask.TaskDAO;
import com.futureinst.model.record.UserRecordInfoDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.TimeLimitUtil;
import com.futureinst.utils.animal.TranslateAnimationUtil;
import com.futureinst.widget.list.MyListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TodayTaskActivity extends BaseActivity {
//    private UserRecordInfoDAO userRecordInfoDAO;
    private LinearLayout ll_top,ll_bottom;

    private View view_novice_task,view_routine_task;
    private MyListView lv_novice,lv_routine;
    private DailyTaskAdapter noviceAdapter;
    private DailyTaskAdapter routineAdapter;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_today_task);
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        setTitle("今日任务");
        initView();
        query_user_record();
        query_user_daily_task();
    }

    private void initView() {
//        userRecordInfoDAO = (UserRecordInfoDAO) getIntent().getSerializableExtra("userRecordInfo");
        ll_top = (LinearLayout) findViewById(R.id.ll_top);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        view_novice_task = LayoutInflater.from(this).inflate(R.layout.view_novice_task,null);
        view_routine_task = LayoutInflater.from(this).inflate(R.layout.view_routine_task,null);
        lv_novice = (MyListView) view_novice_task.findViewById(R.id.lv_novice);
        lv_routine = (MyListView) view_routine_task.findViewById(R.id.lv_routine);
        noviceAdapter = new DailyTaskAdapter(this);
        routineAdapter = new DailyTaskAdapter(this);

        lv_routine.setAdapter(routineAdapter);
        lv_novice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskDAO item = (TaskDAO) noviceAdapter.getItem(position);
                switch (item.getFlag()) {
                    case 0://未完成
                        MyToast.getInstance().showToast(TodayTaskActivity.this, "任务未完成", 0);
                        break;
                    case 1://已完成，可领取
                        get_award_for_new_task(item.getName(), item.getAward(), position);
                        break;
                }

            }
        });
        lv_routine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskDAO item = (TaskDAO) routineAdapter.getItem(position);
                switch (item.getFlag()) {
                    case 0://未完成
                        MyToast.getInstance().showToast(TodayTaskActivity.this, "任务未完成", 0);
                        break;
                    case 1://已完成，可领取
                        get_daily_task_award(item.getName(), item.getAward(), position);
                        break;
                }
            }
        });




    }

    //新手任务
    private void initNoviceView(UserRecordInfoDAO userRecordInfoDAO){
        if(judgeNoviceViewIsShow(userRecordInfoDAO.getTask_list(),userRecordInfoDAO.getUser_record().getUser().getOnOffSet())){
            ll_top.removeAllViews();
            lv_novice.setAdapter(noviceAdapter);
            noviceAdapter.setList(sortNoviceTasks(userRecordInfoDAO.getTask_list(), userRecordInfoDAO.getUser_record().getUser().getOnOffSet()));
            ll_top.addView(view_novice_task);
        }else{
            ll_top.setVisibility(View.GONE);
        }
    }
    //name + "_awd"已领取；name + "_fin"已完成(判断是否为新注册用户)
    private boolean judgeNoviceViewIsShow(List<TaskDAO> tasks,List<String> awards){
        for(TaskDAO task : tasks){
            if(!awards.contains(task.getName()+"_awd")){
                return true;
            }
        }
        return false;
    }
    //新手任务排序
    private List<TaskDAO> sortNoviceTasks(List<TaskDAO> tasks,List<String> awards){
        List<TaskDAO> list = new ArrayList<>();
        List<TaskDAO> award_list = new ArrayList<>();
        for(TaskDAO task : tasks){
            if(!awards.contains(task.getName()+"_awd") && awards.contains(task.getName() + "_fin")){
                task.setFlag(1);//完成
                list.add(task);
            }else if(awards.contains(task.getName()+"_awd")){
                task.setFlag(2);//领取
                award_list.add(task);
            }else if(!awards.contains(task.getName()+"_awd") && !awards.contains(task.getName() + "_fin")){
                task.setFlag(0);//进行中
                task.setProgress("0/1");
                list.add(task);
            }
        }
        list.addAll(award_list);
        return list;
    }

    //常规任务
    private void initRoutineView(DailyTaskMapInfoDAO dailyTaskMapInfoDAO){
        ll_bottom.addView(view_routine_task);
        routineAdapter.setList(sortRoutineTasks(dailyTaskMapInfoDAO.getTask_list(),dailyTaskMapInfoDAO.getDaily_task()));
    }
    //排序
    private List<TaskDAO> sortRoutineTasks(List<TaskDAO> tasks,Map<String,Object> dailyTaskMap){
        List<TaskDAO> list = new ArrayList<>();
        List<TaskDAO> list_award = new ArrayList<>();
        for(TaskDAO task : tasks){
            if(task.getStatus()!= 1){
                break;
            }
            if(((List<String>)dailyTaskMap.get("finishedTasks")).contains(task.getName())
                    && !((List<String>)dailyTaskMap.get("awardedTasks")).contains(task.getName())){//已完成
                task.setFlag(1);
                list.add(task);
            }else if(((List<String>)dailyTaskMap.get("awardedTasks")).contains(task.getName())){//已领取
                task.setFlag(2);
                list_award.add(task);
            }else if(!((List<String>)dailyTaskMap.get("finishedTasks")).contains(task.getName())
                    && !((List<String>)dailyTaskMap.get("awardedTasks")).contains(task.getName())){//进行中
                task.setFlag(0);
                if(task.getNum() > 1){
                    task.setProgress((int)((double)(dailyTaskMap.get(task.getNumName())))+"/"+task.getNum());
                }else{
                    task.setProgress("0/1");
                }
                list.add(task);
            }
        }
        list.addAll(list_award);
        return list;
    }

    //查询今日任务
    private void query_user_daily_task() {
        progressDialog.progressDialog();
        httpResponseUtils.postJson_1(
                httpPostParams.getPostParams(
                        PostMethod.query_user_daily_task.name(),
                        PostType.daily_task.name(),
                        httpPostParams.query_user_daily_task(preferenceUtil.getID()
                                + "", preferenceUtil.getUUid())),
                DailyTaskMapInfoDAO.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response)
                            throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null)
                            return;
                        DailyTaskMapInfoDAO dailyTaskInfo = (DailyTaskMapInfoDAO) response;
                        initRoutineView(dailyTaskInfo);
                    }
                });
    }
    //领取今日任务奖励
    private void get_daily_task_award (final String task_name,final int award,final int position) {
        progressDialog.progressDialog();
        httpResponseUtils.postJson_1(
                httpPostParams.getPostParams(
                        PostMethod.get_daily_task_award.name(),
                        PostType.daily_task.name(),
                        httpPostParams.get_daily_task_award(preferenceUtil.getID()
                                + "", preferenceUtil.getUUid(), task_name)),
                DailyTaskInfoDAO.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response)
                            throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) {
                            return;
                        }
                        DailyTaskInfoDAO dailyTaskInfo = (DailyTaskInfoDAO) response;
                        MyToast.getInstance().showToast(TodayTaskActivity.this, "您获取" + award + "未币", 1);
                        routineAdapter.setAwarded(position);
                        View child_from = lv_routine.getChildAt(position - lv_routine.getFirstVisiblePosition());
                        int item_height = child_from.getHeight();
                        final TaskDAO item = (TaskDAO) routineAdapter.getItem(position);
                        int [] location_from = new int[2];
                        child_from.getLocationInWindow(location_from);
                        TranslateAnimationUtil.slideView(child_from, 0f, 0f, 0f, (routineAdapter.getCount() - 1 - position) * item_height,
                                800, 20, new TranslateAnimationUtil.OnAnimationFinished() {
                                    @Override
                                    public void onAnimationFinished() {
                                        routineAdapter.removeView(position);
                                        routineAdapter.insertView(routineAdapter.getCount(), item);

                                    }
                                });
                    }
                });
    }
    //领取新手任务奖励
    private void get_award_for_new_task (final String task_name,final int award,final int position) {
        progressDialog.progressDialog();
        httpResponseUtils.postJson_1(
                httpPostParams.getPostParams(
                        PostMethod.get_award_for_new_task.name(),
                        PostType.user_info.name(),
                        httpPostParams.get_award_for_new_task(preferenceUtil.getID()
                                + "", preferenceUtil.getUUid(), task_name)),
                BaseModel.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response)
                            throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null) {
                            return;
                        }
                        MyToast.getInstance().showToast(TodayTaskActivity.this, "您获取" + award + "未币", 1);
                        noviceAdapter.setAwarded(position);
                        View child_from = lv_novice.getChildAt(position - lv_novice.getFirstVisiblePosition());
                        int item_height = child_from.getHeight();
                        final TaskDAO item = (TaskDAO) noviceAdapter.getItem(position);
                        int [] location_from = new int[2];
                        child_from.getLocationInWindow(location_from);
                        TranslateAnimationUtil.slideView(child_from, 0f, 0f, 0f, (noviceAdapter.getCount() - 1 - position) * item_height,
                                800, 20, new TranslateAnimationUtil.OnAnimationFinished() {
                                    @Override
                                    public void onAnimationFinished() {
                                        noviceAdapter.removeView(position);
                                        noviceAdapter.insertView(noviceAdapter.getCount(), item);
                                        if (judgeIsAwardNoviceTasks(noviceAdapter.getList())) {
                                            transferAnimal(ll_top, ll_bottom);
                                        }
                                    }
                                });
                    }
                });


    }



    private boolean judgeIsAwardNoviceTasks(List<TaskDAO> tasks){
        for(TaskDAO task : tasks){
            if(task.getFlag() != 2)
                return false;
        }
        return true;
    }
    private void transferAnimal(final View viewFrom,View viewTo){
            int[] location_start = new int[2];
            int[] location_stop = new int[2];
        viewFrom.getLocationInWindow(location_start);
        viewTo.getLocationInWindow(location_stop);
        TranslateAnimationUtil.slideView(viewFrom, 0f, 0f, 0f, (float) (location_stop[1] - location_start[1] - viewFrom.getHeight() + viewTo.getHeight()),
                800, 20, new TranslateAnimationUtil.OnAnimationFinished() {
                    @Override
                    public void onAnimationFinished() {
                        viewFrom.setVisibility(View.GONE);
                    }
                });
        TranslateAnimationUtil.slideView(viewTo,0f,0f,0f,(float)(location_start[1]-location_stop[1]),
                800,20,null);
    }


    // 获取用户信息
    private void query_user_record() {
        progressDialog.progressDialog();
        httpResponseUtils
                .postJson(
                        httpPostParams.getPostParams(PostMethod.query_user_record.name(), PostType.user_info.name(),
                                httpPostParams.query_user_record(preferenceUtil.getID() + "",
                                        preferenceUtil.getUUid())),
                        UserRecordInfoDAO.class, new PostCommentResponseListener() {
                            @Override
                            public void requestCompleted(Object response) throws JSONException {
                                progressDialog.cancleProgress();
                                if (response == null)
                                    return;
                                UserRecordInfoDAO userRecordInfoDAO = (UserRecordInfoDAO) response;
                                initNoviceView(userRecordInfoDAO);
                            }
                        });
    }
}
