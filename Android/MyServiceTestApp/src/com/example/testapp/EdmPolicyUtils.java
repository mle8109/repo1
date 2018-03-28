package com.example.testapp;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.enterprise.ApplicationPolicy;
import android.app.enterprise.EnterpriseDeviceManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

public class EdmPolicyUtils {
    private static final String LOG_TAG = "EdmPolicyUtils";
    
    public static void startApplication(Context ctxt, String packageName) {
        PackageManager pm = ctxt.getPackageManager();
        Intent launch = pm.getLaunchIntentForPackage(packageName);
        if (launch == null) {
            Log.w(LOG_TAG, packageName + " is not installed");
        } else {
            launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctxt.startActivity(launch);
        }
    }

    public static boolean stopApplication(Context ctxt, String packageName) {
        EnterpriseDeviceManager edm = (EnterpriseDeviceManager) ctxt
                .getSystemService(EnterpriseDeviceManager.ENTERPRISE_POLICY_SERVICE);
        ApplicationPolicy appPolicy = edm.getApplicationPolicy();
        return appPolicy.stopApp(packageName);
    }

    public static String SHA1(String text) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha1hash = md.digest();
		String digestStr =Base64.encodeToString(sha1hash,0);
		return digestStr;
	} 

	public static boolean verifyDigest( String userKey, String inDigest ) throws Exception{
		boolean ret = false;
		if (userKey == null || inDigest == null ){
			return ret;
		}

		String outDigest = SHA1 (userKey);
		Log.d(LOG_TAG, "Digest: " + " inDigest: #" + inDigest + "# outDigest: #" + outDigest +"#");
		if (outDigest.equals(inDigest)){
			Log.d(LOG_TAG, " Digests are equal");
			ret = true;
		}else {
			Log.d(LOG_TAG," Digests are not equal");
		}
		return ret;
	}
}
