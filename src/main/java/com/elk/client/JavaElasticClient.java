package com.elk.client;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import com.elk.model.ArchiveFile;
import com.elk.model.FileDesc;
import com.elk.model.MessageFile;
import com.elk.tools.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;

public  class JavaElasticClient {
	
	public static final  RestHighLevelClient CLIENT;
	
	
	static {
		 CLIENT = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));
	}
	
	public static void createIndexIfNotExist(String name) throws IOException{
		
		GetIndexRequest getRequest = new GetIndexRequest(name);
		boolean exists = CLIENT.indices().exists(getRequest, RequestOptions.DEFAULT);
		
		if(!exists){
			CreateIndexRequest request = new CreateIndexRequest(name);
			request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 2));
			CreateIndexResponse createIndexResponse = CLIENT.indices().create(request, RequestOptions.DEFAULT);
			Constant.LOGGER.info("[ELK Index creation]response id: " + createIndexResponse.index());
		} 
		
	}
	
	public static void persiteData(String indexName, FileDesc file) throws IOException{
		file.setEndProcessingDate(new Date());
		IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.source(new ObjectMapper().writeValueAsString(file), XContentType.JSON);
        IndexResponse indexResponse = CLIENT.index(indexRequest, RequestOptions.DEFAULT);
        Constant.LOGGER.info("[File Persisted]:"+file.getName());
	}
	
	public static void persiteData(String indexName, MessageFile msg) throws IOException{
		msg.setEndProcessingDate(new Date());
		IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.source(new ObjectMapper().writeValueAsString(msg), XContentType.JSON);
        IndexResponse indexResponse = CLIENT.index(indexRequest, RequestOptions.DEFAULT);
        Constant.LOGGER.info("[File Persisted]:"+msg.getSubject());
	}
	
	public static void persiteData(String indexName, ArchiveFile archiveFile) throws IOException{
		archiveFile.setEndProcessingDate(new Date());
		IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.source(new ObjectMapper().writeValueAsString(archiveFile), XContentType.JSON);
        IndexResponse indexResponse = CLIENT.index(indexRequest, RequestOptions.DEFAULT);
        Constant.LOGGER.info("[File Persisted]:"+archiveFile.getName());
	}

}




















