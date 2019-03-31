package com.duobang.builder.builder_android.util.permission;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * ms  用于进行动态权限认证的情况
 * （shouldShowRequestPermissionRationale()，这个方法可用可不用）具体就行用来当用户已经拒绝权限之后再一次请求权限的时候进行认证
 */
public class EasyPermission {

    private AppCompatActivity appCompatActivity;
    private ArrayList<String> permissionLists;
    private PermissionCallBack permissionCallBack;
    private int permissionCode;

    public static Builder with(AppCompatActivity appCompatActivity) {
        return new Builder(appCompatActivity);
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public void setPermissionCode(int permissionCode) {
        this.permissionCode = permissionCode;
    }

    public void setPermissionLists(ArrayList<String> permissionLists) {
        this.permissionLists = permissionLists;
    }

    public void setPermissionCallBack(PermissionCallBack permissionCallBack) {
        this.permissionCallBack = permissionCallBack;
    }

    private boolean checkHasPermission(String permission) {
        return ContextCompat.checkSelfPermission(appCompatActivity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == this.permissionCode) {
            // If request is cancelled, the result arrays are empty.

            if (grantResults.length > 0
                    && checkAllGrantResults(grantResults)) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                this.permissionCallBack.grantSuccess();

            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                this.permissionCallBack.grantFail("error");
            }
        }
    }

    private boolean checkAllGrantResults(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermission() {
        ArrayList<String> permissions = new ArrayList<>();
        for (String permission : permissionLists) {
            if (!checkHasPermission(permission)) {
                permissions.add(permission);
            }
        }
        String[] permissionArrays = permissions.toArray(new String[]{});
        if (permissionArrays.length <= 0) {
            permissionCallBack.granted();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                appCompatActivity.requestPermissions(permissionArrays, permissionCode);
        }


    }

    public static class Builder {
        AppCompatActivity appCompatActivity;
        PermissionCallBack permissionCallBack;
        ArrayList<String> permissionLists;
        int permissionCode;

        public Builder(AppCompatActivity appCompatActivity) {
            this.appCompatActivity = appCompatActivity;
        }

        public Builder addPermissions(String... permissions) {
            permissionLists = new ArrayList();
            for (String permission : permissions) {
                permissionLists.add(permission);
            }
            return this;
        }

        public Builder addPermissionCode(int permissionCode) {
            this.permissionCode = permissionCode;
            return this;
        }

        public Builder requestGrant(PermissionCallBack permissionCallBack) {
            this.permissionCallBack = permissionCallBack;
            return this;
        }

        public EasyPermission build() {
            EasyPermission easyPermission = new EasyPermission();
            easyPermission.setPermissionLists(permissionLists);
            easyPermission.setPermissionCode(permissionCode);
            easyPermission.setPermissionCallBack(permissionCallBack);
            easyPermission.setAppCompatActivity(appCompatActivity);
            easyPermission.requestPermission();
            return easyPermission;

        }
    }
}
