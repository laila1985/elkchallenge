package com.elk.services.file;

import com.auxilii.msgparser.Message;
import com.auxilii.msgparser.MsgParser;
import com.auxilii.msgparser.attachment.Attachment;
import com.auxilii.msgparser.attachment.FileAttachment;
import com.elk.client.JavaElasticClient;
import com.elk.model.ArchiveFile;
import com.elk.model.FileDesc;
import com.elk.model.MessageFile;
import com.elk.services.Folder.FolderProcessService;
import com.elk.services.Folder.FolderProcessServiceImpl;
import com.elk.tools.Constant;
import com.elk.tools.Tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ArchiveFileProcess implements FileProcess {

	private ImageFileProcess imgProcess;
	private EmailFileProcess emailFileProcess;
	private TextFileProcess textProcess;
	private Tools tools;

	{
		
		imgProcess= new ImageFileProcess();
		emailFileProcess= new EmailFileProcess() ;
		textProcess=new TextFileProcess();
		tools = new Tools();
	}

	private static final int BUFFER_SIZE = 1024;
	private static final int MAX_ENTRY_SIZE = 104857600;
	private static final int MAX_ENTRY = 1000;

	private static final String OUTPUT_DIR = Constant.ARCHIVED_INPUT_DIRECTORY + "temp";

	public final static List<String> TYPE_IMG = Arrays.asList("png", "gif", "jpg", "tiff");
	public final static List<String> TYPE_DOC = Arrays.asList("text,", "doc", "pdf", "txt");

	@Override
	public void parseFile(FileDesc file) {
		Constant.LOGGER.info("[INFO]: Processing Archive Parsing file :" + file.getName());
		JavaElasticClient javaElasticClient = new JavaElasticClient();

		Constant.LOGGER.info("[INFO]: Processing Image Parsing file :" + file.getName());

		try {
			String indexName = file.getIndexName();
			String adaptedIndexName = indexName.toLowerCase().replaceAll("\\s", "");
			javaElasticClient.createIndexIfNotExist(adaptedIndexName);

			Constant.LOGGER.info("Unzipping file " + file.getName() + "...");
			//file.setName(file.getName());
			file.setStartProcessingDate(new Date());

			File processedFile = new File(file.getPath());
			unzipFile(processedFile);
			// Parse file content
			ArchiveFile archiveFile = readFromInputStream(OUTPUT_DIR);
			archiveFile.setEndProcessingDate(new Date());
			javaElasticClient.persiteData(adaptedIndexName, archiveFile);

			// Delete temporar folder  
			File tempFile = new File(OUTPUT_DIR);
			tempFile.delete();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArchiveFile readFromInputStream(String outputDir) throws IOException {
		File folder = new File(OUTPUT_DIR);
		List <FileDesc> processedFiles= new ArrayList<FileDesc>();
		List <MessageFile> processedMessageFiles= new ArrayList<MessageFile>();
		List<File> listFiles = tools.getFilesList(folder);
		if (listFiles != null && !listFiles.isEmpty()) {
			for (final File fileEntry : folder.listFiles()) {
				FileDesc file = new FileDesc(fileEntry, "Started", false);
				if (Constant.IMG_FILES_EXT.contains(file.getExtension())) {
					processedFiles.add(imgProcess.readFromInputStream(file));

				}
				if (Constant.TEXT_FILES_EXT.contains(file.getExtension())) {
					processedFiles.add(textProcess.readFromInputStream(file));
				}
				
				if (Constant.EMAIL_FILE_EXT.contains(file.getExtension())) {
					processedMessageFiles.add(emailFileProcess.readFromInputStream(file));
				}

			}
		} else {
			Constant.LOGGER.info("[INFO]: The folder is empty :" + outputDir);
			Constant.LOGGER
					.fine("[STOPPED]: Processing folder stopped:" + Constant.INPUT_DIRECTORY + Constant.ARCHIVE_FOLDER);
		}
		return null;
	}

	public static final void unzipFile(File inputZipFile) throws IOException {
		// DATA BUFFER
		byte[] data = new byte[BUFFER_SIZE];

		// variable to store total number of extracted byte
		long total = 0;

		// variable to store number of zip entries extracted
		long numEntries = 0;

		// Extract Zip entries into output folder
		try {
			// Create output directory
			File outDir = new File(OUTPUT_DIR);
			if (!outDir.exists()) {
				outDir.mkdir();
			}

			// Obtain zip file content using a buffered input stream
			FileInputStream fis = new FileInputStream(inputZipFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ZipInputStream zis = new ZipInputStream(bis);

			ZipEntry zEntry;
			File file = null;

			// Extract zip contents:
			while ((zEntry = zis.getNextEntry()) != null) {
				String zename = validateFile(zEntry.getName(), OUTPUT_DIR);
				if (zEntry.isDirectory()) {
					file = new File(OUTPUT_DIR, zename);
					if (!file.exists())
						file.mkdir();
					continue;
				}

				file = new File((OUTPUT_DIR + "/" + zename));
				int size;

				// return absolute abstract pathname for the new file:
				System.out.println("Extracting entry :" + file.getAbsoluteFile());
				// Write files to destination directory:
				FileOutputStream fos = new FileOutputStream(outDir + "/" + zename);
				BufferedOutputStream ds = new BufferedOutputStream(fos, BUFFER_SIZE);
				while (total + BUFFER_SIZE <= MAX_ENTRY_SIZE && (size = zis.read(data, 0, BUFFER_SIZE)) != -1) {
					ds.write(data, 0, BUFFER_SIZE);
					total += size;
				}

				// Close the current zip entry and position the stream fo
				// reading the next entry
				zis.closeEntry();

				// flush and close the destination stream for the extracted
				// contents
				ds.close();

				// Increment the number of entries
				numEntries++;

				if (numEntries > MAX_ENTRY)
					throw new IllegalStateException("Too many files to be extracted from the zip archive");

				if (total > MAX_ENTRY_SIZE)
					throw new IllegalStateException("file to be decompressed too large");

			}
			// close the zipe and file input streams
			zis.close();
			// report that decompression is complete
			fis.close();

			System.out
					.println("Extraction complete. succefully extracted " + numEntries + " entries from Zip archive.");
			System.out.println("Total number of bytes extracted" + total);

		} catch (IOException ioe) {
			throw new IOException("Could not unzip file " + inputZipFile, ioe);
		}
	}

	private static String validateFile(String file, String outDir) throws IOException {

		File f = new File(file);
		// obtain the file canonical path
		String cPath = f.getCanonicalPath();
		File fileID = new File(outDir);
		String cID = fileID.getCanonicalPath();
		if (cPath.startsWith(cID)) {
			return cPath;
		} else {
			throw new IllegalStateException("Files is outside target output directory");
		}

	}

}