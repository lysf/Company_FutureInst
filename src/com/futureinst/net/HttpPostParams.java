package com.futureinst.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class HttpPostParams {
	private static HttpPostParams httpPostParams;
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
	//注册
	public String regist(String mobilePhoneNumber,String code,String pwd){
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobilePhoneNumber", mobilePhoneNumber);
		map.put("code", code);
		map.put("ctype", "android");
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
	public String update_user(String uuid,String user_id,String name,String description){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		map.put("name", name);
		map.put("description", description);
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
	//首页查询事件
	public String query_event(String tag,String order){
		Map<String, String> map = new HashMap<String, String>();
		map.put("tag", tag);
		map.put("order", order);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//根据事件组id查询事件
	public String query_event_by_group_id(String id){
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
	public String query_event_clear(String user_id,String uuid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/**添加订单
	 * type 1-限价买进 2-市价买进 3-限价卖空 4-市价卖空；price,num,event_id,uuid,user_id 
	 *	当type 为2或4时，不需要price属性 
	 */
	public String add_order (String user_id,String uuid,String type,String price,String num,String event_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("type", type);
		map.put("price", price);
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
		map.put("user_id", user_id);
		map.put("uuid", uuid);
		map.put("event_id", event_id);
		map.put("scope", scope);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//查询排名
	public String get_rank(){
		Map<String, Object> map = new HashMap<String, Object>();
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
	//查询个人分类战绩
	public String query_user_tag_record(String user_id,String uuid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	//对账单
	public String query_user_check(String user_id,String uuid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("user_id", user_id);
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
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String query_comment(String event_id,int attitude){
		Map<String, String> map = new HashMap<String, String>();
		map.put("event_id", event_id);
		map.put("attitude", attitude+"");
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
	public String query_follow(String user_id,String uuid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("uuid", uuid);
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
//		map.put("head_image", head_image);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	
}
