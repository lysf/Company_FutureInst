package com.futureinst.home;
/**
 * 记录服务器系统时间，事件倒计时
 * @ClassName:  SystemTimeUtile   
 * @Description:TODO 
 * @author: huihaoyan 
 * @date:   2015-6-26 下午2:24:16   
 *
 */
public class SystemTimeUtile {
	private Long systemTime;
	private boolean flag = true;

	private SystemTimeUtile(Long time) {
		this.systemTime = time;
		runTime();
	}

	private static SystemTimeUtile systemTimeUtile;

	public static SystemTimeUtile getInstance(Long time) {
		if (systemTimeUtile == null)
			systemTimeUtile = new SystemTimeUtile(time);
		return systemTimeUtile;
	}

	public Long getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Long systemTime) {
		this.systemTime = systemTime;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	private void runTime() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (flag) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					systemTime += 1000;
				}
			}
		}).start();
	}
}
