package com.example.da1_qldh_yuii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Welcome extends AppCompatActivity {
    private static final int RC_READ_MEDIA_IMAGES_PERMISSION = 123;
    private static final int RC_READ_EXTERNAL_STORAGE_PERMISSION = 456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Trong phương thức onCreate hoặc bất kỳ phương thức nào khác mà bạn muốn yêu cầu quyền
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Yêu cầu quyền READ_MEDIA_IMAGES cho Android 13 (3) trở lên

            requestReadMediaImagesPermission();
        } else {
            // Yêu cầu quyền READ_EXTERNAL_STORAGE cho các phiên bản SDK thấp hơn
            requestReadExternalStoragePermission();
        }
        runApp();

    }

    @AfterPermissionGranted(RC_READ_MEDIA_IMAGES_PERMISSION)
    private void requestReadMediaImagesPermission() {
        String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            // Quyền đã được cấp
            // Tiếp tục xử lý tại đây
            runApp();
        } else {
            // Quyền chưa được cấp, yêu cầu từ người dùng
            EasyPermissions.requestPermissions(this, "Yêu cầu quyền đọc ảnh",
                    RC_READ_MEDIA_IMAGES_PERMISSION, permissions);
        }
    }
    @AfterPermissionGranted(RC_READ_EXTERNAL_STORAGE_PERMISSION)
    private void requestReadExternalStoragePermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            // Quyền đã được cấp
            // Tiếp tục xử lý tại đây
            runApp();
        } else {
            // Quyền chưa được cấp, yêu cầu từ người dùng
            EasyPermissions.requestPermissions(this, "Yêu cầu quyền đọc bộ nhớ",
                    RC_READ_EXTERNAL_STORAGE_PERMISSION, permissions);
        }
    }

    // Phương thức sau sẽ được gọi sau khi người dùng đồng ý hoặc từ chối cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void runApp(){

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Welcome.this, DangNhap.class));
                finish();
            }

        },3000);
    }
}