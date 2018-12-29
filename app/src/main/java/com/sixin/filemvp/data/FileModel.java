package com.sixin.filemvp.data;

import java.io.File;

public interface FileModel {

    //TODO TODO-MVP中将接口至于其中

    void readFiles(IRead iRead);

    void deleteFile(File file,IDelete iDelete);
}
