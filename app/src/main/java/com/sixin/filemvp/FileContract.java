package com.sixin.filemvp;

import java.io.File;
import java.util.List;

public interface FileContract {

    interface View extends BaseView<Preseneter> {
        void showLoading();

        void showEmpty();

        void showError();

        void showContent(List<File> files);

        void removeItem(int position);

        void toast(String msg);

        boolean isActive();
    }


    interface Preseneter extends BasePresenter{

        void readFiles();

        void deleteFile(File file,int position);
    }
}
