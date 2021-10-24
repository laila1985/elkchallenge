package com.elk.services;

import com.elk.model.Folder;
import com.elk.services.Folder.FolderProcessService;
import com.elk.services.Folder.FolderProcessServiceImpl;
import com.elk.services.file.ProcessFileThread;
import com.elk.tools.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DirectoriesManagerServiceImpl implements DirectoriesManagerService {

    FolderProcessService folderProcessService;

    {
        folderProcessService = new FolderProcessServiceImpl();
    }

    public void processAllFolders() {

        List<ProcessFileThread> ProcessedThread = new ArrayList<ProcessFileThread>();
        // pool : pool des threads d'import
        ThreadPoolExecutor pool = new ThreadPoolExecutor(Constant.THREAD_NUMBER, Constant.THREAD_NUMBER, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        try {
                processFolders(pool, Constant.FOLDERS, ProcessedThread);
        } catch (Exception e) {
            pool.shutdown();
            throw e;
        }
        
        
    }


    private void processFolders(ThreadPoolExecutor pool, List<String> folders, List<ProcessFileThread> processedThread) {

        Constant.LOGGER.fine("[START]: Start processing folders");
        for (String folder : Constant.FOLDERS) {
            // Creating and launching threads per directory
            Constant.LOGGER.info("[START]: Processing Folder :"+ folder);
            ProcessFileThread thread = new ProcessFileThread(folder,folderProcessService);
            pool.execute(thread);
            processedThread.add(thread);
        }
        Constant.LOGGER.fine("[END]: Processing folders");
    }




	@Override
	public void getPerformanceStatistics() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void getStatistics() {
		// TODO Auto-generated method stub
		
	}



}
