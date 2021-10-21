package com.elk.services.Folder;

import com.elk.model.Folder;
import com.elk.services.file.*;
import com.elk.tools.Constant;

import java.util.logging.Logger;

public class FolderProcessFactory {

    public static Folder getFileProcessFactory(String folderName) throws IllegalStateException{
        if(folderName==null){
            throw new IllegalStateException("WARN: Please the name of the folder");
        }

        if(folderName!=null && Constant.FOLDERS.contains(folderName)){
            return new Folder(folderName);
        }else{
            throw new IllegalStateException("WARN: UNKNEW value, please recheck the entred value");
        }
    }
}
