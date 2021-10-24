package com.elk.model;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

public class FileDesc extends File {
    private Long id;
    private String name;
    private String extension;
    private String status;;
    private String content;
    private LocalDate startProcessingDate;
    private LocalDate endProcessingDate;
    private String path;
    private String indexName;
    
    

    public FileDesc(String paramString) {
		super(paramString);
	}
    
    
	public FileDesc(File file, String status, Boolean parent) {
		super(file.getAbsolutePath());
		this.name = file.getName();
		int index = this.name.lastIndexOf('.');
	    if(index > 0) {
	      String extension = this.name.substring(index + 1);
	      setExtension(extension);
	    }
	    this.indexName="index"+this.name.substring(0,index).toLowerCase().replaceAll("[^\\p{Alpha}\\p{Digit}]+","");
		this.status=status;
		this.startProcessingDate=LocalDate.now();
		this.path= file.getAbsolutePath();
		
	}
    
 


	public FileDesc(String filename, Boolean parent,String parentName) {
    	super(filename);
		this.name = filename;
		int index = this.name.lastIndexOf('.');
	    if(index > 0) {
	      String extension = this.name.substring(index + 1);
	      setExtension(extension);
	    }
		this.status = status;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
    
	
}
