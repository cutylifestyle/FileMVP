package com.sixin.filemvp.files;

import com.sixin.filemvp.data.FileManager;
import com.sixin.filemvp.data.FileModel;

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
    public void release() {
        mView = null;
        System.gc();
    }

    @Override
    public void readFiles() {
        mView.showLoading();
        mFileManager.readFiles(new FileModel.IRead() {
            @Override
            public void readSuccess(List<File> files) {
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
    public void readFilesByFormatName(String formatName) {
        //TODO 考虑代码复用
        mView.showLoading();
        mFileManager.readFilesByFormatName(formatName,new FileModel.IRead() {
            @Override
            public void readSuccess(List<File> files) {
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
        mFileManager.deleteFile(file,new FileModel.IDelete() {
            @Override
            public void deleteSuccess() {
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
