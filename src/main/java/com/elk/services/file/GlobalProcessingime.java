package com.elk.services.file;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.elk.tools.Constant;

public class GlobalProcessingime{

	private static RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("localhost", 9200, "http")));

	public static void main(String[] args) throws IOException {

		GetIndexRequest getIndexRequest = new GetIndexRequest("*").indicesOptions(IndicesOptions.lenientExpandOpen());
		String[] indices = client.indices().get(getIndexRequest, RequestOptions.DEFAULT).getIndices();

		for (String indice : indices) {

			if (indice.startsWith("index")) {

				SearchRequest searchRequest = new SearchRequest();
				searchRequest.indices(indice);
				SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
				searchSourceBuilder.query(QueryBuilders.matchAllQuery());
				searchRequest.source(searchSourceBuilder);
				Map<String, Object> map = null;

				try {

					SearchResponse searchResponse = null;
					searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
					if (searchResponse.getHits().getTotalHits().value > 0) {
						SearchHit[] searchHit = searchResponse.getHits().getHits();
						for (SearchHit hit : searchHit) {
							map = hit.getSourceAsMap();
							
							
							
							LocalTime startProcessingDate = map.get("startProcessingDate") != null
									? LocalTime.ofNanoOfDay((long) map.get("startProcessingDate")) : null;
							LocalTime endProcessingDate = map.get("endProcessingDate") != null
									? LocalTime.ofNanoOfDay((long)map.get("startProcessingDate")): null;
							String name = map.get("name") != null ? map.get("name").toString() : null;
							String subject = map.get("subject") != null ? map.get("subject").toString() : null;

							if (name != null) {
								Constant.LOGGER.fine("the file" + name + "has been processed in :"
										+ Duration.between(endProcessingDate, startProcessingDate) + ", startProcessingDate:"+startProcessingDate+",endProcessingDate:"+endProcessingDate);
							}
							if (subject != null) {
								Constant.LOGGER.fine("the mail " + subject + "has been processed in :"
										+ Duration.between(endProcessingDate, startProcessingDate)+ ", startProcessingDate:"+startProcessingDate+",endProcessingDate:"+endProcessingDate);
							}
						}

					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
	}

}
