package com.sixin.filemvp;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.classic.common.MultipleStatusView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements FileContract.View, PermissionUtils.OnPermissionListener {
    //TODO 项目分包
    //TODO 整体架构图
    //TODO 测试修改View，而不影响model的说法
    //TODO 使用mvc写一套
    //TODO 需求变更：文件分类显示
    //TODO 集成findBugs插件
    //TODO 屏幕适配方案
    private Unbinder mUnbinder;

    @BindView(R.id.rlv_file)
    RecyclerView mRlvFile;

    @BindView(R.id.msv_file)
    MultipleStatusView mMsvFile;

    private FileAdapter mFileAdapter;

    private FileContract.Preseneter mFilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);

        initRlv();
        //TODO 考虑权限申请放置的位置
        String[] requestPermissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        PermissionUtils.requestPermissions(this,
                                            Config.CODE_REQUEST_PERMISSIONS,
                                            requestPermissions,
                                            this,
                                            new PermissionUtils.RationaleHandler(){

                                                @Override
                                                protected void showRationale() {
                                                    LogUtils.d("showRationale");
                                                    requestPermissionsAgain();
                                                }
                                            });
    }

    private void initRlv() {
        mRlvFile.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(MainActivity.this,
                                            requestCode,
                                            permissions,
                                            grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        PermissionUtils.releaseListener();
        App.getRefWatcher(getApplicationContext()).watch(this);
    }

    @Override
    public void showLoading() {
        if (mMsvFile != null) {
            mMsvFile.showLoading();
        }
    }

    @Override
    public void showEmpty() {
        if (mMsvFile != null) {
            mMsvFile.showEmpty();
        }
    }

    @Override
    public void showError() {
        if (mMsvFile != null) {
            mMsvFile.showError();
        }
    }

    @Override
    public void showContent(List<File> files) {
        if (mMsvFile != null && files != null) {
            LogUtils.d("showContent");
            mFileAdapter = new FileAdapter(R.layout.item_file,files);
            mRlvFile.setAdapter(mFileAdapter);
            mMsvFile.showContent();
        }
    }

    @Override
    public boolean isActive() {
        return ActivityUtils.isActivityExists(this);
    }

    @Override
    public void setPresenter(FileContract.Preseneter presenter) {

    }

    @Override
    public void onPermissionGranted() {
        LogUtils.d("权限申请成功");
        mFilePresenter = new FilePresenter(this);
        mFilePresenter.readFiles();
    }

    @Override
    public void onPermissionDenied(String[] deniedPermissions) {
        LogUtils.d("权限被拒绝");
        Toast.makeText(this, "无权限读取本地数据", Toast.LENGTH_SHORT).show();
        showEmpty();
    }
}
