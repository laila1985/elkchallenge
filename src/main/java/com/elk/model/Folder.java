package com.elk.model;

import com.elk.tools.Constant;

public class Folder {

    private String name;
    private String pathName;
    private String status;
    private Integer processedFileCount=0;
    private Integer failedProcessedFileCount=0;

    public Folder(String name) {
        this.name = name;
        this.pathName=Constant.INPUT_DIRECTORY+ name;
    }
    public Folder() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProcessedFileCount() {
        return processedFileCount;
    }

    public void setProcessedFileCount(Integer processedFileCount) {
        this.processedFileCount = processedFileCount;
    }

    public Integer getFailedProcessedFileCount() {
        return failedProcessedFileCount;
    }

    public void setFailedProcessedFileCount(Integer failedProcessedFileCount) {
        this.failedProcessedFileCount = failedProcessedFileCount;
    }
}
