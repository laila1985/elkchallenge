package com.elk.model;

import java.io.File;
import java.util.Date;
import java.util.List;

public class ArchiveFile extends File{

	private String name;
	private Date startProcessingDate;
	private Date endProcessingDate;
	private String pathName;
	private Integer processedFileCount = 0;
	private Integer failedProcessedFileCount=0;

	private List<FileDesc> subFile;
	private List<MessageFile> subMailFile;

	public ArchiveFile(String name) {
		super(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartProcessingDate() {
		return startProcessingDate;
	}

	public void setStartProcessingDate(Date startProcessingDate) {
		this.startProcessingDate = startProcessingDate;
	}

	public Date getEndProcessingDate() {
		return endProcessingDate;
	}

	public void setEndProcessingDate(Date endProcessingDate) {
		this.endProcessingDate = endProcessingDate;
	}

	public List<FileDesc> getSubFile() {
		return subFile;
	}

	public void setSubFile(List<FileDesc> subFile) {
		this.subFile = subFile;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public Integer getProcessedFileCount() {
		return processedFileCount;
	}

	public void setProcessedFileCount(Integer processedFileCount) {
		this.processedFileCount = processedFileCount;
	}

	public Integer getFailedProcessedFileCount() {
		return failedProcessedFileCount;
	}

	public void setFailedProcessedFileCount(Integer failedProcessedFileCount) {
		this.failedProcessedFileCount = failedProcessedFileCount;
	}

	public List<MessageFile> getSubMailFile() {
		return subMailFile;
	}

	public void setSubMailFile(List<MessageFile> subMailFile) {
		this.subMailFile = subMailFile;
	}

	

}
