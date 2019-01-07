package com.sixin.filemvp.multifiles;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.sixin.filemvp.App;
import com.sixin.filemvp.R;
import com.sixin.filemvp.files.FileAdapter;
import com.sixin.filemvp.files.FileContract;
import com.sixin.filemvp.files.FilePresenter;
import com.sixin.filemvp.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MultiFilesFragment extends Fragment implements FileContract.View, BaseQuickAdapter.OnItemClickListener {

    private static final String ARG_PARAM = "title";

    private String mTitle;

    private Unbinder mUnbinder;

    @BindView(R.id.rlv_multi_files)
    RecyclerView mRlvMultiFiles;

    @BindView(R.id.msv_file)
    MultipleStatusView mMsvFile;

    private FileAdapter mAdapter;

    private FilePresenter mPresenter;


    public MultiFilesFragment() {
    }

    public static MultiFilesFragment newInstance(String title) {
        MultiFilesFragment fragment = new MultiFilesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multi_files, container, false);
        mUnbinder = ButterKnife.bind(this,view);
        init();
        mPresenter = new FilePresenter(this);
        mPresenter.readFilesByFormatName(mTitle);
        return view;
    }

    private void init() {
        mRlvMultiFiles.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new FileAdapter(R.layout.item_file1, new ArrayList<>());
        mRlvMultiFiles.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        App.getRefWatcher(getContext().getApplicationContext()).watch(this);
    }

    //TODO UI显示部分的逻辑重复了
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
            mAdapter.addData(files);
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
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
    //TODO 碎片中应该如何判断
    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mPresenter != null && mAdapter != null) {
            mPresenter.deleteFile(mAdapter.getItem(position),position);
        }
    }

}
