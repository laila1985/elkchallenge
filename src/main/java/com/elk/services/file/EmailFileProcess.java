package com.elk.services.file;

import com.auxilii.msgparser.Message;
import com.auxilii.msgparser.MsgParser;
import com.auxilii.msgparser.attachment.Attachment;
import com.auxilii.msgparser.attachment.FileAttachment;
import com.elk.client.JavaElasticClient;
import com.elk.model.FileDesc;
import com.elk.model.MessageFile;
import com.elk.tools.Constant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class EmailFileProcess implements FileProcess {
	
	private JavaElasticClient javaElasticClient=new JavaElasticClient();

    @Override
    public void parseFile(FileDesc file) {

    	

        Constant.LOGGER.info("[INFO]: Processing Email Parsing file :"+file.getName());
       
        try {
        	javaElasticClient.createIndexIfNotExist(file.getIndexName());
        	MessageFile messageDesc=readFromInputStream(new File(file.getPath()));
        	messageDesc.setEndProcessingDate(new Date());
        	
        	
        	if(messageDesc!=null){
        		javaElasticClient.persiteData(file.getIndexName(), messageDesc);
        	}
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    public MessageFile readFromInputStream(File processedFile) throws IOException {
    		    MsgParser msgp = new MsgParser();
    		    Message msg = msgp.parseMsg(processedFile.getPath());
    		    
    		    MessageFile messageDesc = new MessageFile(msg.getFromEmail(), msg.getFromName(), msg.getSubject(), msg.getBodyText());
    		    List<Attachment> attachedFile=new ArrayList<>();

    		    List <Attachment> atts = msg.getAttachments();
    		    if(atts!=null && atts.size()>0){
    		    	for (Attachment att : atts) {
    	    		      if (att instanceof FileAttachment) {
    	    		        FileAttachment file = (FileAttachment) att;
    	    		        attachedFile.add(file);
    	    		      }
    	    		      
    	    		    }
    		    	messageDesc.setAttachement(attachedFile);
    		    }
    		    
		return messageDesc;
	}
    
	
}