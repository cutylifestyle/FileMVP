package com.sixin.filemvp.files;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.sixin.filemvp.App;
import com.sixin.filemvp.Config;
import com.sixin.filemvp.R;
import com.sixin.filemvp.utils.ActivityUtils;
import com.sixin.filemvp.utils.LogUtils;
import com.sixin.filemvp.utils.PermissionUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements FileContract.View, PermissionUtils.OnPermissionListener, BaseQuickAdapter.OnItemClickListener {
    //TODO Glide源码分析
    //TODO 2：需求变更：文件分类显示
    //TODO 屏幕适配方案
    //TODO 集成侧滑删除功能
    //TODO 3：整体架构图
    //TODO 2：测试修改View，而不影响model的说法
    //TODO 二维码的制作以及二维码的扫描
    //TODO 3:单元测试
    private Unbinder mUnbinder;
    @BindView(R.id.rlv_file)
    RecyclerView mRlvFile;

    @BindView(R.id.msv_file)
    MultipleStatusView mMsvFile;

    private FileAdapter mAdapter;
    //TODO 对比TODO-mvp，集合不出现在view层
    private List<File> mFiles;

    private FileContract.Preseneter mFilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        initRlv();

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
            mFiles = files;
            mAdapter = new FileAdapter(R.layout.item_file1, mFiles);
            mRlvFile.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(this);
            mMsvFile.showContent();
        }
    }

    @Override
    public void removeItem(int deletePosition) {
        if (mAdapter != null && deletePosition >=0) {
            mAdapter.remove(deletePosition);
        }
    }

    @Override
    public void toast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mFiles != null && mFiles.size() > 0 && mFilePresenter != null) {
            mFilePresenter.deleteFile(mFiles.get(position),position);
        }
    }
}
