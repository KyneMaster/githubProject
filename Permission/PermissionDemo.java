package com.duobang.builder.builder_android.util.permission;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * 动态权限申请使用样本
 */
public class PermissionDemo extends AppCompatActivity implements PermissionCallBack {

    private EasyPermission mEasyPermission;

    private void checkNeedPermission(int code) {
        mEasyPermission = mEasyPermission
                .with(PermissionDemo.this)
                .addPermissions(Manifest.permission.CAMERA)
                .addPermissionCode(code)
                .requestGrant(this)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mEasyPermission.onPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void granted() {
        Toast.makeText(this, "11111111111", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void grantSuccess() {
        Toast.makeText(this, "22222222222", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void grantFail(String onError) {
        Toast.makeText(this, "33333333333", Toast.LENGTH_SHORT).show();
    }
}
