package com.sixin.filemvp;

import java.io.File;
import java.util.List;

public interface IRead {

    void readSuccess(List<File> files);

    void readFail();

}
