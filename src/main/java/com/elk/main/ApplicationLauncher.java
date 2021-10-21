package com.elk.main;

import com.elk.services.*;
import com.elk.tools.Constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ApplicationLauncher {

    private static DirectoriesManagerService directoriesManagerService;

    static{
        directoriesManagerService=new DirectoriesManagerServiceImpl();
    }

    public static void main(String[] args) {

        int defaultNbThreads = 4;
        int nbThreads = defaultNbThreads;

        for (int i = 0; i < args.length; i++) {
            // on verifie que i n est pas le dernier element du tableau
            if (i < args.length - 1) {
                if (args[i].equals("-nbThreads")) {
                    nbThreads = Integer.valueOf(args[++i]);
                    if (nbThreads <= 0) {
                        nbThreads = defaultNbThreads;
                    }
                }
            }
        }
        try {
            directoriesManagerService.processAllFolders();
        } catch (Exception e) {
            Constant.LOGGER.severe("[WARNING]:Unexcepted exception was thrown"+ e.getMessage());
            System.exit(1);
        }

    }

}