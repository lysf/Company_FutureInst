package com.futureinst.todaytask;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.global.TaskType;
import com.futureinst.model.dailytask.DailyTaskInfoDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyToast;
import com.futureinst.utils.TimeLimitUtil;

import org.json.JSONException;

import java.util.List;

public class TodayTaskActivity extends BaseActivity {
    private LinearLayout[] ll_tasks;
    private ImageView[] iv_checks;
    private TextView[] tv_contents;
    private TextView[] tv_icons;
    private TextView[] tv_status;
    private TextView[] tv_task_progress;

    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_today_task);
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        setTitle("今日任务");
        initView();
        addListener();
        query_user_daily_task();
    }

    private void initView() {
        ll_tasks = new LinearLayout[5];
        ll_tasks[0] = (LinearLayout)findViewById(R.id.ll_task_1);
        ll_tasks[1] = (LinearLayout)findViewById(R.id.ll_task_2);
        ll_tasks[2] = (LinearLayout)findViewById(R.id.ll_task_3);
        ll_tasks[3] = (LinearLayout)findViewById(R.id.ll_task_4);
        ll_tasks[4] = (LinearLayout)findViewById(R.id.ll_task_5);

        iv_checks = new ImageView[5];
        iv_checks[0] = (ImageView)findViewById(R.id.iv_check_box_am);
        iv_checks[1] = (ImageView)findViewById(R.id.iv_check_box_pm);
        iv_checks[2] = (ImageView)findViewById(R.id.iv_check_box_normal_1);
        iv_checks[3] = (ImageView)findViewById(R.id.iv_check_box_normal_2);
        iv_checks[4] = (ImageView)findViewById(R.id.iv_check_box_normal_3);

        tv_contents = new TextView[5];
        tv_contents[0] = (TextView)findViewById(R.id.tv_am);
        tv_contents[1] = (TextView)findViewById(R.id.tv_pm);
        tv_contents[2] = (TextView)findViewById(R.id.tv_normal_1);
        tv_contents[3] = (TextView)findViewById(R.id.tv_normal_2);
        tv_contents[4] = (TextView)findViewById(R.id.tv_normal_3);

        tv_icons = new TextView[5];
        tv_icons[0] = (TextView)findViewById(R.id.tv_am_icon);
        tv_icons[1] = (TextView)findViewById(R.id.tv_pm_icon);
        tv_icons[2] = (TextView)findViewById(R.id.tv_normal_1_icon);
        tv_icons[3] = (TextView)findViewById(R.id.tv_normal_2_icon);
        tv_icons[4] = (TextView)findViewById(R.id.tv_normal_3_icon);

        tv_status = new TextView[5];
        tv_status[0] = (TextView)findViewById(R.id.tv_am_status);
        tv_status[1] = (TextView)findViewById(R.id.tv_pm_status);
        tv_status[2] = (TextView)findViewById(R.id.tv_normal_1_status);
        tv_status[3] = (TextView)findViewById(R.id.tv_normal_2_status);
        tv_status[4] = (TextView)findViewById(R.id.tv_normal_3_status);

        tv_task_progress = new TextView[3];
        tv_task_progress[0] = (TextView)findViewById(R.id.tv_normal_1_progress);
        tv_task_progress[1] = (TextView)findViewById(R.id.tv_normal_2_progress);
        tv_task_progress[2] = (TextView)findViewById(R.id.tv_normal_3_progress);
    }
    private void initData(DailyTaskInfoDAO dailyTaskInfo){
        if(dailyTaskInfo.getDaily_task().getFinishedTasks()!=null
                && dailyTaskInfo.getDaily_task().getFinishedTasks().size()>0){//已领取

            complete(dailyTaskInfo.getDaily_task().getAwardedTasks());
        }
        //TRADEAM
        if(!dailyTaskInfo.getDaily_task().getAwardedTasks().contains(TaskType.TRADEAM) ){
            if(!dailyTaskInfo.getDaily_task().getFinishedTasks().contains(TaskType.TRADEAM)){
                switch (TimeLimitUtil.judgeTaskAM(dailyTaskInfo.getCurr_time())){
                    case -1://时间未到
                        ll_tasks[0].setClickable(false);
                        iv_checks[0].setImageResource(R.drawable.today_task_check_clickable);
                        tv_contents[0].setSelected(false);
                        tv_icons[0].setSelected(false);
                        tv_status[0].setText("时间未到");
                        tv_status[0].setSelected(true);
                        break;
                    case 1://已过期
                        ll_tasks[0].setClickable(false);
                        iv_checks[0].setImageResource(R.drawable.today_task_check_unclickable);
                        tv_contents[0].setSelected(true);
                        tv_icons[0].setSelected(true);
                        tv_status[0].setText("已过期");
                        tv_status[0].setSelected(true);
                        break;
                    default:
                        ll_tasks[0].setClickable(false);
                        iv_checks[0].setImageResource(R.drawable.today_task_check_clickable);
                        tv_contents[0].setSelected(false);
                        tv_icons[0].setSelected(false);
                        tv_status[0].setText("未达标");
                        tv_status[0].setSelected(true);
                        break;
                }
            }else{
                ll_tasks[0].setClickable(true);
                iv_checks[0].setImageResource(R.drawable.today_task_check_clickable);
                tv_contents[0].setSelected(false);
                tv_icons[0].setSelected(false);
                tv_status[0].setText("点击领取");
                tv_status[0].setSelected(false);
            }
        }
        //TRADEPM
        if(!dailyTaskInfo.getDaily_task().getAwardedTasks().contains(TaskType.TRADEPM)){
            if(!dailyTaskInfo.getDaily_task().getFinishedTasks().contains(TaskType.TRADEPM)){
                switch (TimeLimitUtil.judgeTaskPM(dailyTaskInfo.getCurr_time())){
                    case -1://时间未到
                        ll_tasks[1].setClickable(false);
                        iv_checks[1].setImageResource(R.drawable.today_task_check_clickable);
                        tv_contents[1].setSelected(false);
                        tv_icons[1].setSelected(false);
                        tv_status[1].setText("时间未到");
                        tv_status[1].setSelected(true);
                        break;

                    case 1://已过期
                        ll_tasks[1].setClickable(false);
                        iv_checks[1].setImageResource(R.drawable.today_task_check_unclickable);
                        tv_contents[1].setSelected(true);
                        tv_icons[1].setSelected(true);
                        tv_status[1].setText("已过期");
                        tv_status[1].setSelected(true);
                        break;
                    default:
                        ll_tasks[1].setClickable(false);
                        iv_checks[1].setImageResource(R.drawable.today_task_check_clickable);
                        tv_contents[1].setSelected(false);
                        tv_icons[1].setSelected(false);
                        tv_status[1].setText("未达标");
                        tv_status[1].setSelected(true);
                        break;
                }
            }else{
                    ll_tasks[1].setClickable(true);
                    iv_checks[1].setImageResource(R.drawable.today_task_check_clickable);
                    tv_contents[1].setSelected(false);
                    tv_icons[1].setSelected(false);
                    tv_status[1].setText("点击领取");
                    tv_status[1].setSelected(false);
            }
        }
        //LOGIN
        if(!dailyTaskInfo.getDaily_task().getAwardedTasks().contains(TaskType.LOGIN)){
                    ll_tasks[2].setClickable(true);
                    iv_checks[2].setImageResource(R.drawable.today_task_check_clickable);
                    tv_contents[2].setSelected(false);
                    tv_icons[2].setSelected(false);
                    tv_status[2].setText("点击领取");
                    tv_status[2].setSelected(false);
                    tv_task_progress[0].setVisibility(View.VISIBLE);
        }
        //TRADE2
        if(!dailyTaskInfo.getDaily_task().getAwardedTasks().contains(TaskType.TRADE2)){
            if(dailyTaskInfo.getDaily_task().getOrderNum()<2){
                ll_tasks[3].setClickable(false);
                iv_checks[3].setImageResource(R.drawable.today_task_check_clickable);
                tv_contents[3].setSelected(false);
                tv_icons[3].setSelected(false);
                tv_status[3].setVisibility(View.GONE);
                tv_task_progress[1].setSelected(true);
            }else{
                ll_tasks[3].setClickable(true);
                iv_checks[3].setImageResource(R.drawable.today_task_check_clickable);
                tv_contents[3].setSelected(false);
                tv_icons[3].setSelected(false);
                tv_status[3].setText("点击领取");
                tv_status[3].setSelected(false);
                tv_task_progress[1].setSelected(false);
                tv_status[3].setVisibility(View.VISIBLE);
            }
            tv_task_progress[1].setVisibility(View.VISIBLE);
            tv_task_progress[1].setText(dailyTaskInfo.getDaily_task().getOrderNum() + "/2");
        }
        //COM2
        if(!dailyTaskInfo.getDaily_task().getAwardedTasks().contains(TaskType.COM2)){
            if(dailyTaskInfo.getDaily_task().getComNum()<2){
                ll_tasks[4].setClickable(false);
                iv_checks[4].setImageResource(R.drawable.today_task_check_clickable);
                tv_contents[4].setSelected(false);
                tv_icons[4].setSelected(false);
                tv_task_progress[2].setSelected(true);
                tv_status[4].setVisibility(View.GONE);
            }else{
                ll_tasks[4].setClickable(true);
                iv_checks[4].setImageResource(R.drawable.today_task_check_clickable);
                tv_contents[4].setSelected(false);
                tv_icons[4].setSelected(false);
                tv_status[4].setText("点击领取");
                tv_status[4].setSelected(false);
                tv_task_progress[2].setSelected(false);
                tv_status[4].setVisibility(View.VISIBLE);
            }
            tv_task_progress[2].setVisibility(View.VISIBLE);
            tv_task_progress[2].setText(dailyTaskInfo.getDaily_task().getComNum() + "/2");
        }



    }
    private void complete(List<String> finishedTasks){
        int i = 0;
        for(String task : finishedTasks){
            if(task.equals(TaskType.TRADEAM)){
                i = 0;
            }else if(task.equals(TaskType.TRADEPM)){
                i = 1;
            }else if(task.equals(TaskType.LOGIN)){
                i = 2;
                tv_task_progress[0].setVisibility(View.GONE);
            }else if(task.equals(TaskType.TRADE2)){
                i = 3;
                tv_task_progress[1].setVisibility(View.GONE);
            }else if(task.equals(TaskType.COM2)){
                i = 4;
                tv_task_progress[2].setVisibility(View.GONE);
            }
            ll_tasks[i].setClickable(false);
            iv_checks[i].setImageResource(R.drawable.today_task_check_complete);
            tv_contents[i].setSelected(true);
            tv_icons[i].setSelected(true);
            tv_status[i].setText("已领取");
            tv_status[i].setVisibility(View.VISIBLE);
            tv_status[i].setSelected(true);
        }
    }
    private void addListener(){
        ll_tasks[0].setOnClickListener(clickListener);
        ll_tasks[1].setOnClickListener(clickListener);
        ll_tasks[2].setOnClickListener(clickListener);
        ll_tasks[3].setOnClickListener(clickListener);
        ll_tasks[4].setOnClickListener(clickListener);
    }
    View.OnClickListener clickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_task_1:
                if(ll_tasks[0].isClickable()){
                    get_daily_task_award(TaskType.TRADEAM);
                }
                break;
            case R.id.ll_task_2:
                if(ll_tasks[1].isClickable()){
                    get_daily_task_award(TaskType.TRADEPM);
                }
                break;
            case R.id.ll_task_3:
                if(ll_tasks[2].isClickable()){
                    get_daily_task_award(TaskType.LOGIN);
                }
                break;
            case R.id.ll_task_4:
                if(ll_tasks[3].isClickable()){
                    get_daily_task_award(TaskType.TRADE2);
                }
                break;
            case R.id.ll_task_5:
                if(ll_tasks[4].isClickable()){
                    get_daily_task_award(TaskType.COM2);
                }
                break;
        }
    }
    };
    //查询今日任务
    private void query_user_daily_task() {
        progressDialog.progressDialog();
        httpResponseUtils.postJson_1(
                httpPostParams.getPostParams(
                        PostMethod.query_user_daily_task.name(),
                        PostType.daily_task.name(),
                        httpPostParams.query_user_daily_task(preferenceUtil.getID()
                                + "", preferenceUtil.getUUid())),
                DailyTaskInfoDAO.class, new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response)
                            throws JSONException {
                        progressDialog.cancleProgress();
                        if (response == null)
                            return;
                        DailyTaskInfoDAO dailyTaskInfo = (DailyTaskInfoDAO) response;
                        initData(dailyTaskInfo);
                    }
                });
    }
    //领取今日任务奖励
    private void get_daily_task_award (String task_name) {
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
                        MyToast.getInstance().showToast(TodayTaskActivity.this, "您获取" + dailyTaskInfo.getAward() + "未币", 1);
                        initData(dailyTaskInfo);
                    }
                });
    }
}
