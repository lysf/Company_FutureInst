package com.futureinst.widget.waterwave;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MySersor 
{
	
	private Sensor sensor;
	
	private SensorManager sensorManager;
	
	private Context context;
	
	private MySensorLisenter mySensorLisenter;
	
	private SensorEventListener lsn;
	
	private float x,y,z;
	
	public MySersor(Context context){
		
		this.context = context;
		
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		lsn = new SensorEventListener()
		{
			
			@Override
			public void onSensorChanged(SensorEvent event)
			{
				// TODO Auto-generated method stub
				 x = event.values[0];
				 y = event.values[1];
				 z = event.values[2];
				 if(mySensorLisenter != null)
				 mySensorLisenter.doRotate(x);
				
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy)
			{
				// TODO Auto-generated method stub
				
			}
		};
		
		
	}
	
	public void setMySensorLisenter(MySensorLisenter mySensorLisenter){
		this.mySensorLisenter = mySensorLisenter;
	}
	
	public void onResume(){
		
		sensorManager.registerListener(lsn, sensor, 
				SensorManager.SENSOR_DELAY_GAME);   
		
		
	}
	
	public void onPasue(){
		sensorManager.unregisterListener(lsn);
	}
	
	interface MySensorLisenter{
		 public void doRotate(float x);
	}
	
}
