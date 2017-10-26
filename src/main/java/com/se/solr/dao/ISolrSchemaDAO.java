package com.se.solr.dao;

public interface ISolrSchemaDAO {

     String getSchemaFromAddress(String domain, String schema, String table);

     String createCore(String domain, String schema, String table);


     String uploadNewSchema(String xml, String domain,String schema, String table);

     String reloadCore(String domain, String schema, String table);

}
