package com.sixin.filemvp;

public interface FileContract {

    interface View extends BaseView<Preseneter> {
        void showLoading();

        void showEmpty();

        void showError();

        void showContent();
    }


    interface Preseneter extends BasePresenter{
        void readFiles();
    }
}
