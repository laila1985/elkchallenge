package com.elk.services.file;

import com.elk.tools.Constant;

public class FileProcessFactory {

    public FileProcess getFileProcessFactory(String Foldername){
        if(Foldername==null){
            return null;
        }

        if(Foldername.equals("archives")){
            return new ArchiveFileProcess();
        }else if (Foldername.equals("images")){
            return new ImageFileProcess();
        }else if(Foldername.equals("text")){
            return new TextFileProcess();
        }else if(Foldername.equals("emails")){
            return new EmailFileProcess();
        }else{
            throw new IllegalStateException("WARN: UNKNEW value, please recheck the entred value");
        }

    }
    
    public FileProcess getFileProcessFactoryByExtension(String extension){
        if(extension==null){
            return null;
        }

        if(Constant.IMG_FILES_EXT.contains(extension)){
            return new ImageFileProcess();
        }else if (Constant.TEXT_FILES_EXT.contains(extension)){
            return new TextFileProcess ();
        }else if(Constant.EMAIL_FILE_EXT.equals("emails")){
            return new EmailFileProcess();
        }else{
            throw new IllegalStateException("WARN: UNKNEW value, please recheck the entred value");
        }

    }
}
