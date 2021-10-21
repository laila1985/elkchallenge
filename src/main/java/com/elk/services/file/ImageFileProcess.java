package com.elk.services.file;

import com.elk.client.JavaElasticClient;
import com.elk.model.FileDesc;
import com.elk.model.MessageFile;
import com.elk.services.file.FileProcess;
import com.elk.tools.Constant;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageFileProcess implements FileProcess {

	@Override
	public void parseFile(FileDesc file) {
		JavaElasticClient javaElasticClient = new JavaElasticClient();

		Constant.LOGGER.info("[INFO]: Processing Image Parsing file :" + file.getName());
		try {
			// Create Indexes
			javaElasticClient.createIndexIfNotExist(file.getIndexName());

			// Parse file content
			readFromInputStream(file);

			javaElasticClient.persiteData(file.getIndexName(), file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FileDesc readFromInputStream(FileDesc file) throws IOException {

		// Parse file content
		BufferedImage myPicture = ImageIO.read(new File(file.getPath()));
		file.setContent(myPicture.toString());

		return file;
	}

}
