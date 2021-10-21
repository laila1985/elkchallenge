package com.elk.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tools {
	
	public  List<File> getFilesList(final File folder) throws IOException {
        List<File> fileList= new ArrayList<File>();
        if(!folder.exists()){
            throw new IllegalStateException("Oups !! Could not find the folder!");
        }
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                getFilesList(fileEntry);
            } else {
                fileList.add(fileEntry);
            }
        }
        return fileList.isEmpty()? null : fileList;
    }

}
