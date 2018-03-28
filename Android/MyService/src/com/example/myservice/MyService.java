package com.example.myservice;

//import com.example.test.IMyService;
//import com.example.test.IMyService.Stub;

import com.example.test.IMyService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
	private final String LOG_TAG = MyService.class.getSimpleName();
	
    @Override
    public void onCreate() {
    	Log.d(LOG_TAG, "onCreate: binder: " + mBinder);
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand - " + intent);
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
    
	private final IMyService.Stub mBinder = new IMyService.Stub() {
		@Override
		public boolean isServiceAvailable() throws RemoteException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int add(int num1, int num2) throws RemoteException {
			Log.d(LOG_TAG, "calling add... num1 =" + num1);

			return MyService.this.add(num1, num2);
		}

		@Override
		public int sub(int num1, int num2) throws RemoteException {
			return MyService.this.sub(num1, num2);
		}
		
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(LOG_TAG, "onBind: mBinder = " + mBinder);
		return mBinder;
	}

	public int add(int num1, int num2) {
		Log.d(LOG_TAG, "add... num1 =" + num1);
		return num1 + num2;
	}
	
	public int sub(int num1, int num2) {
		return num1 - num2;
	}

}
