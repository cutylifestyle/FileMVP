package com.sixin.filemvp.data;

import java.io.File;
import java.util.List;

public interface FileModel {

    interface IDelete{
        void deleteSuccess();

        void deleteFail();
    }

    interface IRead{
        void readSuccess(List<File> files);

        void readFail();
    }

    void readFiles(IRead iRead);

    void deleteFile(File file,IDelete iDelete);

    void readFilesByFormatName(String formatName,IRead iRead);
}
