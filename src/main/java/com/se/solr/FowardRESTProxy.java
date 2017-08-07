package com.se.solr;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/***
 *  * The following API template allows the retrieving and posting of Schema document
 * to Solr Nodes
 *
 *  @implNote I would have liked to perform these request directly from the JavaScript,
 * but because of Same-Origin Policy (SOP) security measure enabled in most browsers,
 * users would most likely see 'Access-Control-Allow-Origin' errors when
 * performing such request accross domains.
 *
 *@author Michael Raney
 */
@RestController
public class FowardRESTProxy {

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

        String request = new SolrRequestBuilder(domain, schema, table).buildSchemaGetRequest();

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(request, String.class);

        return cleanXMLOfNewLineAndSpaces(result);
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
                                  @RequestParam(name = "table")String table) throws SAXException, ParserConfigurationException, IOException, TransformerException{


        String request = new SolrRequestBuilder(domain, schema, table).buildSchemaPostRequest();

        Resource resource  = new ByteArrayResource(xml.trim().getBytes());

        RestTemplate restTemplate = new RestTemplate();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<String> response = restTemplate.exchange(request, HttpMethod.POST, new HttpEntity<Resource>(resource), String.class);

        return response.toString();
    }

    @RequestMapping(method = RequestMethod.GET, path= "/reloadCore")

    /***
     * Reload Solr Core
     *
     * @implNote Needed after uploading new Schema inorder to take effect
     */
    public String reloadCore(@RequestParam(name = "domain")String domain,
                             @RequestParam(name = "schema")String schema,
                             @RequestParam(name = "table")String table){

        String request = new SolrRequestBuilder(domain, schema, table).buildReloadCoreRequest();

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(request, String.class);

        return result;
    }


    /***
     * Remove formatting from XML
     * @implNote  Pretty Crude way to do this, but this is low throughput app...
     * @param rawXML containing spaces and newlines
     * @return  xml without formatting.
     */
    private String cleanXMLOfNewLineAndSpaces(String rawXML){
        return rawXML.replaceAll("\\t", " ").replaceAll("\\r\\n|\\r|\\n", "")
                .replaceAll(">\\s+<", "><");
    }

}


