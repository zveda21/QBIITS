package com.example.rafael.my_application.helpers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import com.example.rafael.my_application.activity.GoogleMapsActivity;

public class Helper {
    public static boolean isPermissionGranted(String permission, Context context, int permissionNumber) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(permission)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions((GoogleMapsActivity)context, new String[]{permission}, permissionNumber);
                return false;
            }
        }
        else {
            return true;
        }
    }

}
