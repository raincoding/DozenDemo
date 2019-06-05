package com.dozen.dozendemo.clear;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dozen.dozendemo.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Dozen on 19-6-5.
 * Describe:
 */
public class ClearActivity extends Activity implements CleanerService.OnActionListener {

    private CleanerService cleanerService;

    private boolean mAlreadyScanned=false;

    List<CacheListItem> cacheListItemList=new ArrayList<>();

    private Button btnStart;
    private Button btnScan;
    private ProgressBar pbState;
    private ProgressBar pbClear;
    private TextView tvShow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        btnScan=findViewById(R.id.btn_clear_scan);
        btnStart=findViewById(R.id.btn_clear_start);
        btnScan.setOnClickListener(btnListener);
        btnStart.setOnClickListener(btnListener);

        pbState=findViewById(R.id.pb_clear_state);
        pbState.setVisibility(View.GONE);

        pbClear=findViewById(R.id.pb_clear_start);
        pbClear.setVisibility(View.GONE);



        tvShow=findViewById(R.id.tv_clear_app);


    }

    View.OnClickListener btnListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_clear_scan:
                    statScan();
                    break;
                case R.id.btn_clear_start:
                    cleanCache();
                    break;
            }
        }
    };


    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            cleanerService=((CleanerService.CleanerServiceBinder)service).getService();
            cleanerService.setOnActionListener(ClearActivity.this);

            if (!cleanerService.isScanning()&&!mAlreadyScanned){
                cleanerService.scanCache();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            cleanerService.setOnActionListener(null);
            cleanerService=null;
        }
    };


    @Override
    public void onScanStarted(Context context) {
        pbState.setVisibility(View.VISIBLE);
    }

    @Override
    public void onScanProgressUpdated(Context context, int current, int max, long cacheSize, String packageName) {
        String s = Formatter.formatShortFileSize(context, cacheSize);
        String tvText=tvShow.getText()+"---"+s+"--"+packageName+"\n";
        tvShow.setText(tvText);
        Log.d("ClearActivity","scan"+":"+max+"---"+current);
        pbState.setMax(max);
        pbState.setProgress(current);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onScanCompleted(Context context, List<CacheListItem> apps) {
        cacheListItemList.clear();
        cacheListItemList.addAll(apps);

        long allSize=0;
        for (CacheListItem item:apps){
            allSize+=item.getCacheSize();
        }

        String s = Formatter.formatShortFileSize(context, allSize);
        btnStart.setText(btnStart.getText()+"--"+s);
        Toast.makeText(getBaseContext(),"扫描完成",Toast.LENGTH_LONG).show();

        pbState.setProgress(pbState.getMax());
    }

    @Override
    public void onCleanStarted(Context context) {
        pbState.setVisibility(View.GONE);
        pbClear.setVisibility(View.VISIBLE);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCleanCompleted(Context context, long cacheSize) {
        Toast.makeText(getBaseContext(),"清理完成",Toast.LENGTH_LONG).show();
        tvShow.setText("清理完成");
        pbClear.setVisibility(View.GONE);
        btnStart.setText("开始清理");
        unbindService(serviceConnection);
    }

    public void statScan(){
        bindService(new Intent(getBaseContext(),CleanerService.class),serviceConnection,Context.BIND_AUTO_CREATE);
    }


    public void cleanCache(){
        if (cleanerService!=null&&!cleanerService.isScanning()&&!cleanerService.isCleaning()&&cleanerService.getCacheSize()>0){
            cleanerService.cleanCache();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        unbindService(serviceConnection);
        statScan();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
