package com.sixin.filemvp.files;

import com.sixin.filemvp.data.FileManager;
import com.sixin.filemvp.data.FileModel;
import com.sixin.filemvp.data.IDelete;
import com.sixin.filemvp.data.IRead;
import com.sixin.filemvp.files.FileContract;

import java.io.File;
import java.util.List;

public class FilePresenter implements FileContract.Preseneter {

    private FileContract.View mView;
    private FileModel mFileManager;

    public FilePresenter(FileContract.View view) {
        if (view == null) {
            throw new NullPointerException("view must not be null");
        }
        mView = view;
        mFileManager = new FileManager();
    }

    @Override
    public void start() {

    }

    @Override
    public void release() {

    }

    @Override
    public void readFiles() {
        mView.showLoading();
        mFileManager.readFiles(new IRead() {
            @Override
            public void readSuccess(List<File> files) {
                //todo 这个部分可能存在内存泄漏
                if (mView != null && mView.isActive()) {
                    if (files != null && files.size() > 0) {
                        mView.showContent(files);
                    }else{
                        mView.showEmpty();
                    }
                }
            }

            @Override
            public void readFail() {
                if (mView != null && mView.isActive()) {
                    mView.showError();
                }
            }
        });
    }

    @Override
    public void deleteFile(File file,int position) {
        mFileManager.deleteFile(file,new IDelete() {
            @Override
            public void deleteSuccess() {
                //todo 这个部分可能存在内存泄漏
                if (mView != null && mView.isActive()) {
                    mView.removeItem(position);
                }
            }

            @Override
            public void deleteFail() {
                if (mView != null && mView.isActive()) {
                    mView.toast("删除文件失败");
                }
            }
        });
    }
}
