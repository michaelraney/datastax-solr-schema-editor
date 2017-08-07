package com.se.solr.dao;

public interface ISolrSchemaDAO {

    public String getSchemaFromAddress(String domain, String schema, String table);

    public String uploadNewSchema(String xml, String domain,String schema, String table);

    public String reloadCore(String domain, String schema, String table);

}
