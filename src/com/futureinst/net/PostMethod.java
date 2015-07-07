package com.futureinst.net;

public enum PostMethod {
	send_smscode,//发送验证码
	add_user_by_phone,//添加用户
	update_user,//修改用户信息
	sign_in_by_phone,//手机登录
	update_pwd,//修改密码
	query_event,//查询事件
	query_event_group,//查询事件组
	query_order,//查询个人订单（未完成订单）
	query_event_clear,//查询清算单
	add_order,//添加订单
	query_single_event,//查询单个事件
	get_rank,//获取排名
	get_faq,//常见问题
	query_user_check,//对账单
	update_order,//撤销订单
	query_comment,//查询评论
	add_comment,//添加评论
	query_follow,//查询我的关注
	operate_follow,//关注操作
	query_user_record ;//查询个人资产战绩等
}
