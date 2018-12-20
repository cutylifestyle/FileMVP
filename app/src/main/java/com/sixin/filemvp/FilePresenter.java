package com.sixin.filemvp;

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
            public void readSuccess() {
                //todo mView可能为空
                //todo 这个部分可能存在内存泄漏
            }

            @Override
            public void readFail() {

            }
        });
    }
}
