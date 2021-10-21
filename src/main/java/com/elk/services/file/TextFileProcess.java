package com.elk.services.file;

import com.elk.client.JavaElasticClient;
import com.elk.model.FileDesc;
import com.elk.model.MessageFile;
import com.elk.services.file.FileProcess;
import com.elk.tools.Constant;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.elasticsearch.core.internal.io.IOUtils;

public class TextFileProcess implements FileProcess {

	@Override
	public void parseFile(FileDesc file) {
		Constant.LOGGER.info("[INFO]: Processing Test Parsing file :" + file.getName());
		JavaElasticClient javaElasticClient = new JavaElasticClient();

		try {
			// Create Indexes
			javaElasticClient.createIndexIfNotExist(file.getIndexName());
		
			// Parse file content
			FileDesc fileDesc=readFromInputStream(file);
			

			javaElasticClient.persiteData(file.getIndexName(), fileDesc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Parsing file
	 * @throws IOException 
	 */
	public FileDesc readFromInputStream(FileDesc file) throws IOException {
		if (Constant.PDF_FILE_EXT.equalsIgnoreCase(file.getExtension())) {
			file.setContent(readFromInputStreamPDF(new File(file.getPath())));
		} else if (Constant.DOC_FILE_EXT.equalsIgnoreCase(file.getExtension())) {
			file.setContent(readFromInputStreamDOC(new File(file.getPath())));
		} else {
			file.setContent(readFromInputStreamTXT(new File(file.getPath())));
		}
		return file;
	}
	
	

	/**
	 * Parsing of the DOC file Content
	 * 
	 * @param file
	 * @return content
	 */

	private String readFromInputStreamDOC(File file) {
		WordExtractor extractor = null;
		try {
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			HWPFDocument document = new HWPFDocument(fis);
			extractor = new WordExtractor(document);
			return extractor.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Parsing of the PDF file Content
	 * 
	 * @param file
	 * @return content
	 */

	private String readFromInputStreamPDF(File file) {
		PdfReader pdfReader;
		try {
			pdfReader = new PdfReader(file.getAbsolutePath());

			// Get the number of pages in pdf.
			int pages = pdfReader.getNumberOfPages();
			String data = "";

			// Iterate the pdf through pages.
			for (int i = 1; i <= pages; i++) {
				// Extract the page content using PdfTextExtractor.
				String pageContent = PdfTextExtractor.getTextFromPage(pdfReader, i);
				data = data.concat(pageContent);
			}
			pdfReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Parsing the TXT file and it s the default parser
	 * 
	 * @param processedFile
	 * @return content
	 * @throws IOException
	 */

	private String readFromInputStreamTXT(File processedFile) throws IOException {
		InputStream inputStream = new FileInputStream(processedFile);
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultStringBuilder.toString();
	}

}