package com.elk.services.Folder;

import com.elk.model.ArchiveFile;
import com.elk.model.FileDesc;
import com.elk.model.Folder;
import com.elk.services.file.FileProcess;
import com.elk.services.file.FileProcessFactory;
import com.elk.tools.Constant;
import com.elk.tools.Tools;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FolderProcessServiceImpl implements FolderProcessService {
	
	private Tools tools=new Tools();


    @Override
    public void processFolder(Folder processedFolder) throws IOException {
        //Get the process file to be executed :
        FileProcessFactory fpf = new FileProcessFactory();
        FileProcess fileProcess= fpf.getFileProcessFactory(processedFolder.getName());

        //GetList of file within the folder:
        File folder= new File(processedFolder.getPathName());
        List<File> listFiles = tools.getFilesList(folder);

        if(listFiles!=null && !listFiles.isEmpty()){
            for (final File fileEntry : folder.listFiles()) {
                Boolean status =fileProcess.processFile(fileEntry,processedFolder);
                if(status){
                    processedFolder.setProcessedFileCount(processedFolder.getProcessedFileCount()+1);
                }else{
                    processedFolder.setFailedProcessedFileCount(processedFolder.getFailedProcessedFileCount()+1);
                }
            }
        }else{
            Constant.LOGGER.info("[INFO]: The folder is empty :"+processedFolder.getName());
            Constant.LOGGER.fine("[STOPPED]: Processing folder stopped:"+processedFolder.getName());
        }

    }
    
    @Override
    public void processFolder(ArchiveFile processedFolder) throws IOException {
        //Get the process file to be executed :
        FileProcessFactory fpf = new FileProcessFactory();
        //GetList of file within the folder:
        File folder= new File(processedFolder.getPathName());
        List<File> listFiles = tools.getFilesList(folder);

        if(listFiles!=null && !listFiles.isEmpty()){
            for (final File fileEntry : folder.listFiles()) {
            	FileDesc fileDes=new FileDesc(fileEntry,"started",false);
            	FileProcess fileProcess= fpf.getFileProcessFactoryByExtension(fileDes.getExtension());
				Boolean status =fileProcess.processFile(fileEntry);
                if(status){
                    processedFolder.setProcessedFileCount(processedFolder.getProcessedFileCount()+1);
                }else{
                    processedFolder.setFailedProcessedFileCount(processedFolder.getFailedProcessedFileCount()+1);
                }
            }
        }else{
            Constant.LOGGER.info("[INFO]: The folder is empty :"+processedFolder.getName());
            Constant.LOGGER.fine("[STOPPED]: Processing folder stopped:"+processedFolder.getName());
        }

        getStatistics(processedFolder);
    }


	private void getStatistics(ArchiveFile processedFolder) {
		// TODO Auto-generated method stub
		
	}
}
