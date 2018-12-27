package com.sixin.filemvp;

import android.os.Environment;

import java.io.File;
import java.util.List;

public class FileManager implements FileModel {

    @Override
    public void readFiles(IRead iRead) {
        String dirPath = Environment.getExternalStorageDirectory() + File.separator + "test";
        File fileDir = new File(dirPath);
        boolean isDir = FileUtils.isDir(fileDir);
        if(!isDir){
            if (iRead != null) {
                iRead.readFail();
            }
        }else{
            List<File> files = FileUtils.listFilesInDir(fileDir);
            if (iRead != null) {
                iRead.readSuccess(files);
            }
        }
    }
}
