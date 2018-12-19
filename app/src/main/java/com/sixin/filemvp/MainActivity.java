package com.sixin.filemvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.classic.common.MultipleStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        mMsvFile.showLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        App.getRefWatcher(getApplicationContext()).watch(this);
    }
}
