package com.elk.services.file;

import com.elk.model.Folder;
import com.elk.services.Folder.FolderProcessFactory;
import com.elk.services.Folder.FolderProcessService;
import com.elk.services.Folder.FolderProcessServiceImpl;

public class ProcessFileThread implements Runnable{


    private int identifiant;
    private Folder folder;
    private FolderProcessService folderProcessService;
    private String folderName;

    public ProcessFileThread(String folder, FolderProcessService folderProcessService) {
        this(folder);
        this.folderProcessService=folderProcessService;
   }

    public ProcessFileThread(int identifiant,  FolderProcessService folderProcessService) {
        super();
        this.identifiant = identifiant;
        this.folderProcessService=folderProcessService;
    }

    public ProcessFileThread(String folderName) {
        this.folder=FolderProcessFactory.getFileProcessFactory(folderName);
        this.folder.setName(folderName);
    }

    @Override
    public void run() {
        try {
               this.folderProcessService.processFolder(this.folder);

        } catch (Exception e) {
            e.getStackTrace();
        }

    }
}
