package com.se.solr.controller;

import com.se.solr.dao.ISolrSchemaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 *  * The following API template allows the retrieving and posting of Schema document
 * to Solr Nodes via REST API
 *
 *  @implNote I would have liked to perform these request directly from the JavaScript,
 * but because of Same-Origin Policy (SOP) security measure enabled in most browsers,
 * users would most likely see 'Access-Control-Allow-Origin' errors when
 * performing such request across domains.
 *
 *@author Michael Raney
 */
@RestController
public class SchemaRESTServicesController {

    @Autowired
    ISolrSchemaDAO solrSchemaDAO;

    /***
     * Retrieve the Schema XML from Solr, pass back to caller with formatting
     * removed
     *
     * @return schemaXML without formatting
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getSchemaFromAddress")
    public String getSchemaFromAddress(@RequestParam(name = "domain")String domain,
                                       @RequestParam(name = "schema")String schema,
                                       @RequestParam(name = "table")String table){ //@RequestParam(name = "address") String address) {

        return solrSchemaDAO.getSchemaFromAddress(domain, schema, table);

    }

    /***
     * Create Core and retrieve the Schema XML from Solr, pass back to caller with formatting
     * removed
     *
     * @return schemaXML without formatting
     */
    @RequestMapping(method = RequestMethod.GET, path = "/createCore")
    public String createCode(@RequestParam(name = "domain")String domain,
                                       @RequestParam(name = "schema")String schema,
                                       @RequestParam(name = "table")String table){ //@RequestParam(name = "address") String address) {

        solrSchemaDAO.createCore(domain, schema, table);

        return solrSchemaDAO.getSchemaFromAddress(domain, schema, table);

    }

    /***
     * Upload new schema to Solr
     * @param xml
     *
     *
     */
    @RequestMapping(method = RequestMethod.POST, path= "/uploadNewSchema")
    public String uploadNewSchema(@RequestParam(name = "xml")String xml,
                                  @RequestParam(name = "domain")String domain,
                                  @RequestParam(name = "schema")String schema,
                                  @RequestParam(name = "table")String table) {


       return solrSchemaDAO.uploadNewSchema(xml, domain, schema, table);
    }


    /***
     * Reload Solr Core
     *
     * @implNote Needed after uploading new Schema inorder to take effect
     */
    @RequestMapping(method = RequestMethod.GET, path= "/reloadCore")
    public String reloadCore(@RequestParam(name = "domain")String domain,
                             @RequestParam(name = "schema")String schema,
                             @RequestParam(name = "table")String table){

        return solrSchemaDAO.reloadCore(domain, schema, table);
    }




}


