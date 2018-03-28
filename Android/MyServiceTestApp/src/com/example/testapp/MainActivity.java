package com.example.testapp;

import com.example.test.MyServiceClient;
import com.example.test.MyServiceClient.IServiceListener;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements IServiceListener{
	private final String LOG_TAG = MainActivity.class.getSimpleName();
	
	private EditText edtNum1;
	private EditText edtNum2;
	private TextView txtStatus;
	private TextView txtResult;
	private Button btnAdd;
	private Button btnSubstract;
	private Button btnStartService;
	private Button btnStopService;
	private Button btnKillService;
	
	private MyServiceClient mClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mClient = MyServiceClient.getInstance(getApplicationContext());
		mClient.setServiceListener(this);
		
		Log.d(LOG_TAG, "mClient =" + mClient);
		
		txtStatus = (TextView) findViewById(R.id.txtStatus);
		edtNum1 = (EditText) findViewById(R.id.edtNum1);
		edtNum1.setText("20");
		edtNum2 = (EditText) findViewById(R.id.edtNum2);
		edtNum2.setText("15");
		txtResult = (TextView) findViewById(R.id.txtResult);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnSubstract = (Button) findViewById(R.id.btnSubstract);
		btnStartService = (Button) findViewById(R.id.btnStart);
		btnStopService = (Button) findViewById(R.id.btnStop);
		btnKillService = (Button) findViewById(R.id.btnKill);
		
		if (btnAdd != null) {
			btnAdd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int num1 =  Integer.parseInt(edtNum1.getText().toString().trim());
					int num2 =  Integer.parseInt(edtNum2.getText().toString().trim());
					Log.d(LOG_TAG, "addNum... num1 = " + num1);
					String result = String.valueOf(addNum(num1, num2));
					Log.d(LOG_TAG, "addNum... result = " + result);
					txtResult.setText(result);
				}
			});
		}
		
		if (btnSubstract != null) {
			btnSubstract.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int num1 =  Integer.parseInt(edtNum1.getText().toString().trim());
					int num2 =  Integer.parseInt(edtNum2.getText().toString().trim());
					String result = String.valueOf(subNum(num1, num2));
					txtResult.setText(result);
				}
			});
		}
		
		if (btnStartService != null) {
			btnStartService.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startService();
				}
			});
		}

		if (btnStopService != null) {
			btnStopService.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					stopService();
				}
			});
		}
		
		if (btnKillService != null) {
			btnKillService.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					killService();
				}
			});
		}
	}

	private int addNum(int num1, int num2) {
		if (mClient != null) {
			return mClient.add(num1, num2);
		}
		return 0;
	}
	
	private int subNum(int num1, int num2) {
		if (mClient != null) {
			return mClient.sub(num1, num2);
		}
		return 0;
	}

	private boolean startService() {
	    Log.d(LOG_TAG, "startService");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.myservice","com.example.myservice.MyService"));
        startService(intent);
        return true;
	}
	
	private boolean stopService() {
        Log.d(LOG_TAG, "stopService");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.myservice","com.example.myservice.MyService"));
        stopService(intent);
        return true;
	}
	
	private boolean killService() {
	    Log.d(LOG_TAG, "killService");
		return EdmPolicyUtils.stopApplication(getApplicationContext(), "com.example.myservice");
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    @Override
    public void onServiceConnected() {
        txtStatus.setText("Service Connected");
    }

    @Override
    public void onServiceDisconnected() {
        txtStatus.setText("Service Disconnected");
    }
}
