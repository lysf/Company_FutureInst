package com.futureinst.net;

public enum PostMethod {
	add_user_with_uuid,//微博微信注册用户
	query_user_with_uuid,//微博微信查询用户
	send_smscode,//发送验证码
	add_user_by_phone,//添加用户
	update_user,//修改用户信息
	sign_in_by_phone,//手机登录
	update_pwd,//修改密码
	query_event_all,//查询事件
	query_event_group,//查询事件组
	query_order,//查询个人订单（未完成订单）
	query_event_clear,//查询清算单
	add_order,//添加订单
	query_single_event,//查询单个事件
	get_rank,//获取排名
	get_faq,//常见问题
	query_user_check,//对账单
	update_order,//撤销订单
	query_event_for_comment,//查询事件评论
	add_comment,//添加评论
	query_follow,//查询我的关注
	operate_follow,//关注操作
	search_event,//事件搜索
	query_single_event_clear,//查询单个事件的清算单
	query_user_record ,//查询个人资产战绩等
	get_android_version,//安卓获取版本号
	add_download,//添加渠道下载标记
	add_feedback,//添加意见反馈
	upload_image,//上传图片
	update_permit,//隐私设置
	operation_peer_follow,//关注操作
	query_follow_me,//关注我的
	query_me_follow,//我关注的
	peer_info_query_user_record,//查询 他人基本信息
	peer_info_query_user_tag_record ,//查询 分类战绩概况
	peer_info_query_event_trade,//查询 预测中事件
	peer_info_query_event_clear,//查询 战绩具体事件
	peer_info_query_follow_me,//查询 关注他的人
	peer_info_query_me_follow,//查询 他关注的人
	query_top_banner,//查询顶部banner广告
	operate_comment,//点赞操作
	get_tag_rank,//分类排名
	query_user_tag_record, //查询分类战绩
	operate_article ,//操作观点
	query_top_article ,//查询发现栏目的观点
	query_comment_for_article ,//查询观点评论
	add_comment_for_article ,//添加评论
	//查询用户观点
	query_user_article,
	peer_info_query_user_article,//查询他人观点
	add_article,//添加观点
	query_user_news,//动态
    query_user_daily_task,//查询当日任务
    get_daily_task_award,//领取当日任务奖励
    get_test_charge,//支付
    get_all_charge_goods,//获取可购买的商品清单
    add_pay_order ,//添加支付订单
    get_charge_for_pay_order ,//为支付订单获取支付凭据
    get_pay_orders_for_user ,//获得用户的所有支付订单
    p2p_give_exchange ,//赠送可消费未币
    find_user ,//查找用户
    get_award_for_new_task  ,//领取新手任务奖励
    get_period_rank,//月日排行
}
