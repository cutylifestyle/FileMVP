package com.sixin.filemvp.data;

import java.io.File;
import java.util.List;

public interface IRead {

    void readSuccess(List<File> files);

    void readFail();

}
