package com.sixin.filemvp;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.classic.common.MultipleStatusView;

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
    private Unbinder mUnbinder;

    @BindView(R.id.rlv_file)
    RecyclerView mRlvFile;

    @BindView(R.id.msv_file)
    MultipleStatusView mMsvFile;

    private FileContract.Preseneter mFilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);

        String[] requestPermissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        PermissionUtils.requestPermissions(this,
                                            Config.CODE_REQUEST_PERMISSIONS,
                                            requestPermissions,
                                            this);
//        mFilePresenter = new FilePresenter(this);
//        mFilePresenter.readFiles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        App.getRefWatcher(getApplicationContext()).watch(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void setPresenter(FileContract.Preseneter presenter) {

    }

    @Override
    public void onPermissionGranted() {
        LogUtils.d("权限申请成功");
    }

    @Override
    public void onPermissionDenied(String[] deniedPermissions) {
        LogUtils.d("权限被拒绝");
    }
}
