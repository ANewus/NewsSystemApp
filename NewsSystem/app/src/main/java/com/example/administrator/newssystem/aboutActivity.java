package com.example.administrator.newssystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.leon.lib.settingview.LSettingItem;

public class aboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        LSettingItem weibo =(LSettingItem)findViewById(R.id.weibo);
        weibo.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Uri uri = Uri.parse("https://weibo.com/u/5517978525");    //设置跳转的网站
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        LSettingItem phone =(LSettingItem)findViewById(R.id.phone);
        phone.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {

                if(ContextCompat.checkSelfPermission(aboutActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    //调用系统授权界面，界面关闭后会调用onRequestPermissionsResult回调函数。
                    ActivityCompat.requestPermissions(aboutActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
                }else{
                    dail();
                }
            }
        });
        LSettingItem www =(LSettingItem)findViewById(R.id.www);
        www.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Uri uri = Uri.parse("https://www.baidu.com");    //设置跳转的网站
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                dail();
            }else{
                Toast.makeText(this, "你拒绝了相关权限授权，无法执行拨号操作！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void dail(){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:188888888888"));
        startActivity(intent);
    }

}
