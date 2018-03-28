package com.example.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyServiceClient {
	private final static String LOG_TAG = MyServiceClient.class.getSimpleName();
	private IMyService mService;
	private Context mContext;
	private boolean mBound;
	
	private static MyServiceClient mClient;
	private IServiceListener mServiceListener;
    private static final String SERVICE_INTENT_FILTER = "com.example.test.service";
    public static final String SERVICE_PKG_NAME = "com.example.myservice";
    public static final String SERVICE_CLASS_NAME = "com.example.myservice.MyService";

    public interface IServiceListener {
        public void onServiceConnected();
        public void onServiceDisconnected();
    }
    
    public void setServiceListener(IServiceListener listener) {
        mServiceListener = listener;
    }
    
	public static MyServiceClient getInstance(Context context) {
		Log.d(LOG_TAG, "MyServiceClient");
		if (mClient == null) {
			mClient = new MyServiceClient(context);
		}
		else {
			mClient.getService();
		}
		return mClient;
	}
	
	public MyServiceClient(Context context) {
		mContext = context;
		getService();
	}
	
	private synchronized IMyService getService() {
		if (mService == null) {
			if (!mBound) {
				
				Intent intentService = new Intent(SERVICE_INTENT_FILTER);
				intentService.setClassName(SERVICE_PKG_NAME, SERVICE_CLASS_NAME);
				ComponentName cn = mContext.startService(intentService);
				Log.d(LOG_TAG, "getService ... cn =" + cn);
				if (cn != null) {
					mBound = mContext.bindService(intentService, mConnection,
                            Context.BIND_AUTO_CREATE);
				}
			}
		}
		return mService;
	}
	
	private final ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e(LOG_TAG, "onServiceConnected");
			mService = IMyService.Stub.asInterface(service);
			sendConnected();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e(LOG_TAG, "Service has unexpectedly disconnected");
			sendDisconnected();
		}
		
	};
	
	private void sendConnected() {
	    if (mServiceListener != null) {
	        mServiceListener.onServiceConnected();
	    }
	}
	
	private void sendDisconnected() {
        mService = null;
        mBound = false;
        if (mServiceListener != null) {
            mServiceListener.onServiceDisconnected();
        }
	}
	
	public int add(int num1, int num2) {
        if (getService() == null) {
            Log.e(LOG_TAG, "Service is not yet ready!!!");
            return 0;
        }
        
		try {
			Log.e(LOG_TAG, "add. sum = " + mService.add(num1, num2));
			return mService.add(num1, num2);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int sub(int num1, int num2) {
        if (getService() == null) {
            Log.e(LOG_TAG, "Service is not yet ready!!!");
            return 0;
        }

		try {
			return mService.sub(num1, num2);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
