package com.futureinst.utils;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class CheckAppIsAction {
	public static boolean isIntentAvailable(Context context, String action) {    
	    final PackageManager packageManager = context.getPackageManager();    
	    final Intent intent = new Intent(action);    
	    List<ResolveInfo> resolveInfo =    
	            packageManager.queryIntentActivities(intent,    
	                    PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.size() > 0;
    }
}
