elk doc :
https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.x/index.html

To connect to Elastic search 
https://simplifyingtech371899608.wordpress.com/2021/02/23/inserting-data-into-elasticsearch-from-java-client/
https://simplifyingtech371899608.wordpress.com/2021/06/28/elasticsearch-finding-distinct-unique-field-values-using-java-program/


to check the created index:
GET indeximages/_search
GET indextext/_search

the input file is configured on the Constant:
 public static final String INPUT_DIRECTORY = "D:/challenge/";
 
 
 The list of the folder to be processed :
ARCHIVE_FOLDER, IMAGES_FOLDER,TEXT_FOLDER, EMAIL_FOLDER Configured on the Constant.java class


The Global GlobalProcessingime class has been implement to check of the processing time and have visibility on the performance,

Two useful Tutorial for getting data :
https://simplifyingtech371899608.wordpress.com/2021/06/28/elasticsearch-finding-distinct-unique-field-values-using-java-program/
https://simplifyingtech371899608.wordpress.com/2021/06/28/elasticsearch-aggregations-with-java-program/



 

