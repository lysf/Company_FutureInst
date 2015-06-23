package com.futureinst.push;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.futureinst.model.push.PushMessageDAO;
import com.futureinst.model.push.PushMessageInfo;
import com.futureinst.sharepreference.SharePreferenceUtil;

import android.content.Context;
import android.os.Environment;

public class PushMessageUtils {
	private SharePreferenceUtil preferenceUtil;
	public  String path ;
	 PushMessageInfo messageInfo;
	 boolean tag = false;
	private static PushMessageUtils pushMessageUtils;
	private PushMessageUtils(Context context){
		preferenceUtil = SharePreferenceUtil.getInstance(context);
		path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/FutureInst/"+preferenceUtil.getID()+"download.obj";
	}
	public static PushMessageUtils getInstance(Context context){
		if(pushMessageUtils == null)
			pushMessageUtils = new PushMessageUtils(context);
		return pushMessageUtils;
	}
	
	private void writeObject(Object o) throws Exception{

	       File f=new File(path);

	       if(f.exists()){
	           f.delete();
	       }
	       FileOutputStream os=new FileOutputStream(f);
	       ObjectOutputStream oos=new ObjectOutputStream(os);
	       oos.writeObject(o);
	       oos.close();
	       os.close();
	    }
	
	@SuppressWarnings("resource")
	public PushMessageInfo readObject() throws Exception{
		File f = new File(path);
	       InputStream is=new FileInputStream(f);
	       ObjectInputStream ois=new ObjectInputStream(is);
	       return (PushMessageInfo)ois.readObject();
	    }
	//添加消息
	public void addObject(PushMessageDAO messageDAO){
		try {
			messageInfo = readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			if(messageDAO != null){
				if(messageInfo == null){
					messageInfo = new PushMessageInfo();
					List<PushMessageDAO> data = new ArrayList<PushMessageDAO>();
					data.add(messageDAO);
					messageInfo.setList(data);
				}else{
					for(int i = 0; i < messageInfo.getList().size(); i++){
						String id = messageInfo.getList().get(i).getId();
						if(messageDAO.getId().equals(id)){
							//已经存在
							tag = true;
							break;
						}
					}
					if(!tag){
						messageInfo.getList().add(0,messageDAO);
						tag = false;
					}
				}
			}
		try {
			writeObject(messageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean isExist(PushMessageDAO messageDAO){
		try {
			messageInfo = readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(messageDAO != null){
			if(messageInfo == null){
				return false;
			}else{
				for(int i = 0; i < messageInfo.getList().size(); i++){
					String id = messageInfo.getList().get(i).getId();
					if(messageDAO.getId().equals(id)){
						//已经存在
						return true;
					}
					if(i == messageInfo.getList().size()-1){
						return false;
					}
				}
			}
		}
		return false;
	}
	//获取未读消息数量
	public int getUnReadMessageCount(){
		int count = 0;
		try {
			messageInfo = readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(messageInfo == null){
			count = 0;
		}else{
			for(int i = 0; i < messageInfo.getList().size(); i++){
				boolean isRead = messageInfo.getList().get(i).isRead();
				if(!isRead){
					count++;
				}
			}
		}
	
		return count;
	}
	//根据id删除消息
	public void delete(long id){
		try {
			messageInfo = readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(messageInfo != null){
			for(int i = 0;i<messageInfo.getList().size();i++){
				if(messageInfo.getList().get(i).getId().equals(id)){
					messageInfo.getList().remove(i);
				}
			}
		}
		try {
			writeObject(messageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//将消息置为已读
	public void setAllRead(){
		try {
			messageInfo = readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(messageInfo != null){
			for(int i = 0;i<messageInfo.getList().size();i++){
				messageInfo.getList().get(i).setRead(true);
			}
		}
		try {
			writeObject(messageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
