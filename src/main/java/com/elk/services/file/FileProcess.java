package com.elk.services.file;

import com.elk.model.FileDesc;
import com.elk.model.Folder;
import com.elk.tools.Constant;

import java.io.File;

public interface FileProcess {

    public abstract void parseFile(FileDesc fileEntry);


    public default Boolean processFile( File fileEntry, Folder processedFolder) {
        Boolean status= Boolean.FALSE;
        try {
        	FileDesc file= new FileDesc(fileEntry, null,true);
            parseFile(file);
            status=Boolean.TRUE;
        }catch(Exception e){
            Constant.LOGGER.severe("[WARNING]: UNEXPECTED ERROR WAS THROWN DURING THE PROCESS OF THE FILE :"+fileEntry.getAbsoluteFile());
            status= Boolean.FALSE;
        }
        return status;
    }
    
    public default Boolean processFile(File fileEntry) {
        Boolean status= Boolean.FALSE;
        try {
        	FileDesc file= new FileDesc(fileEntry, null,true);
            parseFile(file);
            status=Boolean.TRUE;
        }catch(Exception e){
            Constant.LOGGER.severe("[WARNING]: UNEXPECTED ERROR WAS THROWN DURING THE PROCESS OF THE FILE :"+fileEntry.getAbsoluteFile());
            status= Boolean.FALSE;
        }
        return status;
    }

}
