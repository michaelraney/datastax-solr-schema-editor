package com.se.solr.dao.rest;

import com.se.solr.dao.ISolrSchemaDAO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class SolrAdminRESTDAO implements ISolrSchemaDAO {

    @Override
    public String getSchemaFromAddress(String domain, String schema, String table) {
        String request = new SolrRequestBuilder(domain, schema, table).buildSchemaGetRequest();

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(request, String.class);

        return cleanXMLOfNewLineAndSpaces(result);
    }

    @Override
    public String uploadNewSchema(String xml, String domain, String schema, String table) {
        String request = new SolrRequestBuilder(domain, schema, table).buildSchemaPostRequest();

        Resource resource  = new ByteArrayResource(xml.trim().getBytes());

        RestTemplate restTemplate = new RestTemplate();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<String> response = restTemplate.exchange(request, HttpMethod.POST, new HttpEntity<Resource>(resource), String.class);

        return response.toString();
    }

    @Override
    public String reloadCore(String domain, String schema, String table) {

        String request = new SolrRequestBuilder(domain, schema, table).buildReloadCoreRequest();

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(request, String.class);

    }
    @Override
    public String createCore(String domain, String schema, String table) {
        String request = new SolrRequestBuilder(domain, schema, table).createCoreRequest();

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(request, String.class);

        return cleanXMLOfNewLineAndSpaces(result);
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
