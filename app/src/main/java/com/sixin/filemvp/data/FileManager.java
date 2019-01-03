package com.sixin.filemvp.data;

import android.os.Environment;

import com.sixin.filemvp.utils.FileUtils;
import com.sixin.filemvp.utils.FormatUtils;

import java.io.File;
import java.io.FileFilter;
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

    @Override
    public void deleteFile(File file,IDelete iDelete) {
        boolean result = FileUtils.deleteFile(file);
        if (result) {
            if (iDelete != null) {
                iDelete.deleteSuccess();
            }
        }else{
            if (iDelete != null) {
                iDelete.deleteFail();
            }
        }
    }

    @Override
    public void readFilesByFormatName(String formatName,IRead iRead) {
        //TODO 代码复用的问题
        String dirPath = Environment.getExternalStorageDirectory() + File.separator + "test";
        File fileDir = new File(dirPath);
        boolean isDir = FileUtils.isDir(fileDir);
        if(!isDir){
            if (iRead != null) {
                iRead.readFail();
            }
        }else{
            List<File> files = FileUtils.listFilesInDirWithFilter(fileDir, new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return FormatUtils.getForamtName(pathname).equalsIgnoreCase(formatName);
                }
            });
            if (iRead != null) {
                iRead.readSuccess(files);
            }
        }
    }
}
