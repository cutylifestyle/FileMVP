package com.sixin.filemvp;

import java.io.File;

public interface FileModel {
    void readFiles(IRead iRead);

    void deleteFile(File file,IDelete iDelete);
}
