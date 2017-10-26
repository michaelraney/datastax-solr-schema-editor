package com.se.solr.dao.cql;

import com.se.solr.dao.ISolrSchemaDAO;

/***
 * TODO Implement VIA CQL Statements
 */
public class SolrAdminCQLDAO implements ISolrSchemaDAO {


    @Override
    public String getSchemaFromAddress(String domain, String schema, String table) {
        return null;
    }

    @Override
    public String uploadNewSchema(String xml, String domain, String schema, String table) {
        return null;
    }

    @Override
    public String reloadCore(String domain, String schema, String table) {
        return null;
    }

    @Override
    public String createCore(String domain, String schema, String table) {
        return null;
    }
}
