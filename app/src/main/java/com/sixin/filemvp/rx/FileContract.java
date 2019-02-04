package com.sixin.filemvp.rx;

import java.io.File;
import java.util.List;

public interface FileContract {

    interface View extends BaseView {
        void showLoading();

        void showEmpty();

        void showError();

        void showContent(List<File> files);

        void removeItem(int position);

        void toast(String msg);
    }


    interface Preseneter extends BasePresenter {

        void readFiles();

        void readFilesByFormatName(String formatName);

        void deleteFile(File file,int position);
    }
}
