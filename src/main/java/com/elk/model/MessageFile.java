package com.elk.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.auxilii.msgparser.attachment.Attachment;

public class MessageFile {
	 private String fromEmail ;
	 private String fromName ;
	 private String subject;
	 private String body;
	 private LocalDate startProcessingDate;
	 private LocalDate endProcessingDate;
	 
	 private List<Attachment> attachement ;

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Attachment> getAttachement() {
		return attachement;
	}

	public void setAttachement(List<Attachment> attachement) {
		this.attachement = attachement;
	}
	

	public LocalDate getStartProcessingDate() {
		return startProcessingDate;
	}

	public void setStartProcessingDate(LocalDate startProcessingDate) {
		this.startProcessingDate = startProcessingDate;
	}

	public LocalDate getEndProcessingDate() {
		return endProcessingDate;
	}

	public void setEndProcessingDate(LocalDate endProcessingDate) {
		this.endProcessingDate = endProcessingDate;
	}


	public MessageFile(String fromEmail, String fromName, String subject, String body) {
		super();
		this.fromEmail = fromEmail;
		this.fromName = fromName;
		this.subject = subject;
		this.body = body;
		this.startProcessingDate= LocalDate.now();
	}

	 

}
