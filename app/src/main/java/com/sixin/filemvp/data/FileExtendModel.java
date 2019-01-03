package com.sixin.filemvp.data;

public interface FileExtendModel extends FileModel {
    //TODO 首先出现这种扩张的原因在于规划期设计不合理，规划期应该将这种变动
    //TODO  考虑在其中，接口设计不合理应该是根本
    void readFilesByFormatName(String formatName,IRead iRead);

}
