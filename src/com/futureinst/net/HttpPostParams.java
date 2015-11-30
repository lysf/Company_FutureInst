package com.futureinst.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.text.TextUtils;

public class HttpPostParams {
	private static HttpPostParams httpPostParams;
    //ctype: android、yingyongbao、360、xiaomi、wandoujia、baidu、anzhi、huawei
    private String ctype = "huawei";

    public static HttpPostParams getInstace(){
		if(httpPostParams == null)
			httpPostParams = new HttpPostParams();
		return httpPostParams;
	}
	//数据交互
	public Map<String, String> getPostParams(String method,String type,String json){
		Map<String, String> map = new HashMap<String, String>();
		map.put("method", method);
		map.put("type", type);
		map.put("query", json);
		return map;
	}
	//添加渠道下载标记
	public String add_download(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ctype", ctype);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//登录
	public String loginJson(String mobilePhoneNumber,String pwd){
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobilePhoneNumber", mobilePhoneNumber);
		map.put("pwd", pwd);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//获取验证码
	public String getAuthCode(String mobilePhoneNumber){
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobilePhoneNumber", mobilePhoneNumber);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//注册 ctype: android、yingyongbao、360、xiaomi、wandoujia、baidu、anzhi
	public String regist(String mobilePhoneNumber,String code,String pwd){
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobilePhoneNumber", mobilePhoneNumber);
		map.put("code", code);
		map.put("ctype", ctype);
		map.put("pwd", pwd);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 修改信息
	 * @Title: update_user   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @param name
	 * @param: @param gender
	 * @param: @param birthday
	 * @param: @param description
	 * @param: @param interest
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String update_user(String uuid,String user_id,String name,String gender,String birthday,String description,String interest){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		map.put("name", name);
		map.put("gender", gender);
		map.put("birthday", birthday);
		map.put("description", description);
		map.put("interest", interest);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 修改用户信息
	 * @Title: update_user   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @param name
	 * @param: @param description
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String update_user(String uuid,String user_id,String name,String description,String headImage){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		if(!TextUtils.isEmpty(name)){
			map.put("name", name);
		}
		map.put("description", description);
		map.put("head_image", headImage);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	public String update_user_headImage(String uuid,String user_id,String headImage){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		map.put("head_image", headImage);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	public String update_user(String uuid,String user_id,String interest){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		map.put("interest", interest);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//上传clientid
	public String update_user_cid(String uuid,String user_id,String cid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		map.put("cid", cid);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//重置密码
	public String resetPassword(String mobilePhoneNumber,String pwd,String code){
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobilePhoneNumber", mobilePhoneNumber);
		map.put("pwd", pwd);
		map.put("code", code);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//归档事件
	public String query_event(int page,String last_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("check", "-1");
		map.put("page", page+"");
		map.put("last_id", last_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//事件
	public String query_event(String tag,String order,int page,String last_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("tag", tag);
		map.put("order", order);
		map.put("page", page+"");
		map.put("last_id", last_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//根据事件组ID查询事件
	public String query_event(int page,String last_id,String group_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("group_id", group_id);
		map.put("page", page+"");
		map.put("last_id", last_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//根据事件组id查询事件
	public String query_event_by_group_id( String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("group_id", id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//根据关键字搜索事件
	public String search_event(String search_str){
		Map<String, String> map = new HashMap<String, String>();
		map.put("search_str", search_str);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//首页查询事件组
	public String query_event_group(String key){
		Map<String, String> map = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//查询个人订单（未完成订单）
	public String query_order(String user_id,String uuid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//查询单个事件的未成交订单
	public String query_order(String user_id,String uuid,String event_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("event_id", event_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//查询个人清算订单
	public String query_event_clear(String user_id,String uuid,int page,String last_id,int tag,String scop){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("page", page+"");
		map.put("last_id", last_id);
		if(tag > 0){
			map.put("event_tag", tag+"");
		}
		if(!TextUtils.isEmpty(scop)){
			map.put("scope", scop);
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**添加订单
	 * type 1-限价买进 2-市价买进 3-限价卖空 4-市价卖空；price,num,event_id,uuid,user_id 
	 *	当type 为2或4时，不需要price属性
	 *mode:为pro表示是专家模式，否则即为简单模式
	 */
	public String add_order (String user_id,String uuid,String type,String price,String num,String event_id,String mode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("type", type);
		map.put("price", price);
		map.put("mode", mode);
		map.put("num", num);
		map.put("event_id", event_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//查询单个事件
	public String query_single_event (String event_id,String scope){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("event_id", event_id);
		map.put("scope", scope);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	public String query_single_event (String user_id,String uuid,String event_id,String scope){
		Map<String, Object> map = new HashMap<String, Object>();
		if(!user_id.equals("0") && !TextUtils.isEmpty(uuid)){
			map.put("user_id", user_id);
			map.put("uuid", uuid);
		}
		map.put("event_id", event_id);
		map.put("scope", scope);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//查询排名
	public String get_rank(String user_id,String uuid){
		Map<String, Object> map = new HashMap<String, Object>();
		if(!user_id.equals("0") && !TextUtils.isEmpty(uuid)){
			map.put("user_id", user_id);
			map.put("uuid", uuid);
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//分项排名
	public String get_tag_rank(String user_id,String uuid,int tag){
		Map<String, String> map = new HashMap<String, String>();
		if(!user_id.equals("0") && !TextUtils.isEmpty(uuid)){
			map.put("user_id", user_id);
			map.put("uuid", uuid);
		}
		map.put("tag",tag+"");
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//查询个人资产战绩等
	public String query_user_record(String user_id,String uuid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
    //分类
	public String query_user_record(String user_id,String uuid,int tag){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		map.put("tag", tag+"");
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//查询个人分类战绩
	public String query_user_tag_record(String user_id,String uuid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//对账单
	public String query_user_check(String user_id,String uuid,int page,String last_id,String scope){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		map.put("scope", scope);//trade --consume
		map.put("page", page+"");
		map.put("last_id", last_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//常见问题
	public String get_faq(){
		Map<String, String> map = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//撤销订单
	public String update_order(String user_id,String uuid,String order_id,String operation){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("order_id", order_id);
		map.put("operation", operation);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 查询评论
	 * @Title: query_comment   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param event_id
	 * @param: @param attitude 1 表示支持， 2 表示反对
	 * 可选:parent_id(父ID) 如果没有添加parent_id表示查询父评论，如果添加了表示查询子评论
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String query_event_for_comment(String user_id,String uuid,String event_id,int attitude,String parent_id){
		Map<String, String> map = new HashMap<String, String>();
		if(!user_id.equals("0") && !TextUtils.isEmpty(uuid)){
			map.put("user_id", user_id);
			map.put("uuid", uuid);
		}
		map.put("event_id", event_id);
		if(attitude == 1 || attitude == 2){
			map.put("attitude", attitude+"");
		}
		if(parent_id!=null){
			map.put("parent_id", parent_id);
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 添加订单
	 * @Title: add_comment   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param user_id
	 * @param: @param uuid
	 * @param: @param event_id
	 * @param: @param attitude
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String add_comment(String user_id,String uuid,String event_id,int attitude,String content){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("event_id", event_id);
		map.put("attitude", attitude+"");
		map.put("content",content);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	/**
	 * 回复评论
	 * @param user_id
	 * @param uuid
	 * @param event_id
	 * @param parent_id
	 * @param reply_to
	 * @param content
	 * @return
	 */
	public String add_comment(String user_id,String uuid,String event_id,String parent_id,String reply_to,String content){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("event_id", event_id);
		map.put("parent_id", parent_id);
		if(!reply_to.equals("0")){
			map.put("reply_to", reply_to);
		}
		map.put("content",content);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 查询我的关注
	 * @Title: query_follow   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param user_id
	 * @param: @param uuid
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String query_follow(String user_id,String uuid,int page,String last_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("page", page+"");
		map.put("last_id", last_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 操作关注
	 * @Title: operate_follow   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param user_id
	 * @param: @param uuid
	 * @param: @param event_id
	 * @param: @param operation  follow
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String operate_follow(String user_id,String uuid,String event_id,String operation){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("event_id", event_id);
		map.put("operation", operation);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 查询单个事件的清算单
	 * @Title: query_single_event_clear   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param user_id
	 * @param: @param uuid
	 * @param: @param event_id
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String query_single_event_clear (String user_id,String uuid,String event_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("event_id", event_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//获取版本号
	public String get_android_version(){
		Map<String, String> map = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 微博微信注册用户
	 * @Title: add_user_with_uuid   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param name
	 * @param: @param gender
	 * @param: @param head_image
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String add_user_with_uuid(String uuid,String name,String gender,String head_image){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("ctype", "android");
		map.put("name", name);
		map.put("gender", gender);
		map.put("head_image", head_image);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 微博微信查询用户
	 * @Title: query_user_with_uuid   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String query_user_with_uuid(String uuid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 添加反馈
	 * @Title: add_feedback   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @param content
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String add_feedback(String uuid,String user_id,String content){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		map.put("content", content);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	public String getCookie(String uuid,String user_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//图片
	public String upLoadFile(String uuid,String user_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//隐私设置
	//permit_key:order(预测中事件),gain(战绩),follow_me（关注我的人）,me_follow（我关注的人）
	//permit_value: all（所有人可看），follow(关注我的人可看)，none（没有人可以看）
	public String update_permit(String uuid,String user_id,String permit_key,String permit_value){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("permit_key", permit_key);
		map.put("permit_value", permit_value);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 好友关注操作
	 * @Title: operation_peer_follow   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @param peer_id
	 * @param: @param operation(操作类型): follow(添加)，unfollow(取消)
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String operation_peer_follow(String uuid,String user_id,String peer_id,String operation){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("peer_id", peer_id);
		map.put("operation", operation);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 关注我的人
	 * @Title: query_follow_me   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String query_follow_me (String uuid,String user_id,int page,String last_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("page", page+"");
		map.put("last_id", last_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 我关注的人
	 * @Title: query_me_follow   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String query_me_follow (String uuid,String user_id,int page,String last_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("page", page+"");
		map.put("last_id", last_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 查询他人基本信息
	 * @Title: peer_info_query_user_record   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @param peer_id 被查询人id
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String peer_info_query_user_record(String uuid,String user_id,String peer_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("peer_id", peer_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 查询分类战绩概览
	 * @Title: peer_info_query_user_tag_record   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @param peer_id 被查询id
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String peer_info_query_user_tag_record(String uuid,String user_id,String peer_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("peer_id", peer_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 查询预测中的事件
	 * @Title: peer_info_query_event_trade   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @param peer_id
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */ 
	public String peer_info_query_event_trade(String uuid,String user_id,String peer_id,int page,String last_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("peer_id", peer_id);
		map.put("page", page+"");
		map.put("last_id", last_id);
		map.put("scope", "trade");
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 查询战绩的具体事件
	 * @Title: peer_info_query_event_clear   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @param peer_id
	 * @param: @param tag :分类的编号 2<=tag<=9
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String peer_info_query_event_clear(String uuid,String user_id,String peer_id,int tag,int page,String last_id,String scop){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("peer_id", peer_id);
		if(tag > 0){
			map.put("event_tag", tag+"");
		}
		map.put("page", page+"");
		map.put("last_id", last_id+"");
		if(!TextUtils.isEmpty(scop)){
			map.put("scope", scop);
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 查询关注他的人
	 * @Title: peer_info_query_follow_me   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @param peer_id
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String peer_info_query_follow_me(String uuid,String user_id,String peer_id,int page,String last_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("peer_id", peer_id);
		map.put("page", page+"");
		map.put("last_id", last_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**
	 * 查询他关注的人
	 * @Title: peer_info_query_me_follow   
	 * @Description: TODO  
	 * @author: huihaoyan  
	 * @param: @param uuid
	 * @param: @param user_id
	 * @param: @param peer_id
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String peer_info_query_me_follow(String uuid,String user_id,String peer_id,int page,String last_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("peer_id", peer_id);
		map.put("page", page+"");
		map.put("last_id", last_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	/**
	 * 评论点赞
	 * @param uuid
	 * @param user_id
	 * @param com_id 评论id
	 * @param operate 操作（like（点赞），unlike（取消点赞））
	 * @return
	 */
	public String operate_comment(String user_id,String uuid,String com_id,String operate){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("com_id", com_id);
		map.put("operate",operate);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	/**
	 * 操作观点
	 * @param user_id
	 * @param uuid
	 * @param id 观点id
	 * @param operate (read(阅读),award(打赏,award:打赏金额),love(点赞),unlove(取消点赞))
	 * @return
	 */
	public String operate_article(String user_id,String uuid,String id,String operate,int award){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("id", id);
		map.put("operate",operate);
		if(operate.equals("award")){
			map.put("award",award+"");
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	/**
	 * 添加观点的评论
	 * @param user_id
	 * @param uuid
	 * @param article_id
	 * @param content
	 * @param parent_id
	 * @param reply_to
	 * @return
	 */
	public String add_comment_for_article(String user_id,String uuid,String article_id,String content,String parent_id,String reply_to){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("article_id", article_id);
		map.put("content",content);
		if(!parent_id.equals("0")){
			map.put("parent_id",parent_id);
		}
		if(!reply_to.equals("0")){
			map.put("reply_to",reply_to);
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	/**
	 * 查看观点评论
	 * @param article_id
	 * @return
	 */
	public String query_comment_for_article(String user_id,String uuid,String article_id){
		Map<String, String> map = new HashMap<String, String>();
		if(!user_id.equals("0") && !TextUtils.isEmpty(uuid)){
			map.put("user_id", user_id);
			map.put("uuid", uuid);
		}
		map.put("article_id", article_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	public String query_comment_for_article(String user_id,String uuid,String article_id,String parent_id){
		Map<String, String> map = new HashMap<String, String>();
		if(!user_id.equals("0") && !TextUtils.isEmpty(uuid)){
			map.put("user_id", user_id);
			map.put("uuid", uuid);
		}
		map.put("article_id", article_id);
		map.put("parent_id", parent_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//查询观点
	public String query_top_article(String user_id,String uuid){
		Map<String, String> map = new HashMap<String, String>();
		if(!user_id.equals("0") && !TextUtils.isEmpty(uuid)){
			map.put("user_id", user_id);
			map.put("uuid", uuid);
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	/**
	 * 查询用户观点列表
	 * @param user_id
	 * @param uuid
	 * @return
	 */
	public String query_user_article(String user_id, String uuid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	/**
	 * 查询他人观点
	 * @param user_id
	 * @param uuid
	 * @param peer_id 要查询的用户的id
	 * @return
	 */
	public String peer_info_query_user_article(String user_id, String uuid, String peer_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("peer_id", peer_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	public String add_article(String user_id, String uuid, String event_id,String title,String detail) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("event_id", event_id);
		map.put("title", title);
		map.put("detail", detail);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//查询用户动态
	public String query_user_news(String user_id, String uuid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

    /**
     * 查询今日任务
     * @param user_id
     * @param uuid
     * @return
     */
    public String query_user_daily_task(String user_id, String uuid){
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", user_id);
        map.put("uuid", uuid);
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    /**
     * 领取任务奖励
     * @param user_id
     * @param uuid
     * @param task_name
     * @return
     */
    public String get_daily_task_award(String user_id, String uuid,String task_name){
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", user_id);
        map.put("uuid", uuid);
        map.put("task_name", task_name);
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

}
