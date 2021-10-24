package com.elk.services.Folder;

import com.elk.model.ArchiveFile;
import com.elk.model.Folder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FolderProcessService{


    void processFolder(Folder processedFolder) throws IOException;

	void processFolder(ArchiveFile processedFolder) throws IOException;
}
