package dataselect.fujisoft.com.rxrequestpermission;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button onePermission,morePermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onePermission = (Button) findViewById(R.id.request_one_permission);
        morePermission = (Button) findViewById(R.id.request_more_permission);
        onePermission.setOnClickListener(this);
        morePermission.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.request_one_permission:
                requestOnePermission();
                break;
            case R.id.request_more_permission:
                requestMorePermission();
                break;
            default:
                break;
        }
    }

    /**
     * 一次申请一个权限
     */
    private void requestOnePermission() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            Toast.makeText(MainActivity.this,"同意",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this,"拒绝",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 一次申请多个权限
     */
    private void requestMorePermission() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.requestEach(Manifest.permission.CALL_PHONE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted){
                            Toast.makeText(MainActivity.this,"同意",Toast.LENGTH_SHORT).show();
                        }else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，并且没有选中『不再询问』,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            Toast.makeText(MainActivity.this,"拒绝",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private static final String TAG = "MainActivity";

}
