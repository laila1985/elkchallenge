package com.elk.tools;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

public interface Constant {

    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static Integer NUMBER_PROCESSED_FILE=0;
    public static Integer NUMBER_FAILED_FILE=0;
    public static Integer TOTAL = 0;

    public final static String ARCHIVE_FOLDER = "archives";
    public final static String IMAGES_FOLDER = "images";
    public final static String TEXT_FOLDER = "text";
    public final static String EMAIL_FOLDER = "emails";

    public static final String INPUT_DIRECTORY = "D:/elkchallenge/";
    public static final String ARCHIVED_INPUT_DIRECTORY = "D:/elkchallenge/archives";

    public static final int THREAD_NUMBER=4;

    public static final int PAUSE_TIME=10000;

    public final static List<String> FOLDERS= Arrays.asList(ARCHIVE_FOLDER, IMAGES_FOLDER,TEXT_FOLDER, EMAIL_FOLDER);
    
    
    public static final String PDF_FILE_EXT="pdf";
    public static final String TXT_FILE_EXT="txt";
    public static final String DOC_FILE_EXT="doc";
    public static final String EMAIL_FILE_EXT="email";
    
    public static final List<String> TEXT_FILES_EXT=Arrays.asList("txt", "doc", "pdf", "odt");
    public static final List<String> IMG_FILES_EXT=Arrays.asList("jpg","png","tiff","gif");

}
