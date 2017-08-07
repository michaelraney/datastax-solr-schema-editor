package com.se.solr.dao.rest;

/***
 *  Helper class to  Build Request for Solr Core
 *
 * @author michaelraney
 *
 */
public class SolrRequestBuilder {

    private static String domain;

    private static String schema;

    private static String table;

    public SolrRequestBuilder(String domain, String schema, String table){

        SolrRequestBuilder.domain = domain;
        SolrRequestBuilder.schema = schema;
        SolrRequestBuilder.table = table;
    }

    /***
     * Build URL to Retrieves Schema from Solr Core
     *
     * example http://localhost:8983/solr/schema.table/admin/file?file=schema.xml&contentType=text/xml;charset=utf-8
     * @return
     */
    public String buildSchemaGetRequest(){

        return String.format("%s/solr/%s.%s//admin/file?file=schema.xml&contentType=text/xml;charset=utf-8", domain, schema, table);
    }

    /***
     * Build URL to POST Schema to Solr Core
     *
     * example :  http://localhost:8983/solr/resource/schema.table/schema.xml
     * @return
     */
    public String buildSchemaPostRequest(){
       
        return String.format("%s/solr/resource/%s.%s/schema.xml", domain, schema, table);
    }

    /***
     * Build URL to Reload Solr Core
     *
     * example :  http://localhost:8983/solr/admin/cores?action=RELOAD&core=schema.table
     * @return
     */
    public String buildReloadCoreRequest(){

        return String.format("%s/solr/admin/cores?action=RELOAD&core=%s.%s", domain, schema, table);

    }

    //
}
