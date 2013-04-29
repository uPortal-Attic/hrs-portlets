package edu.wisc.cypress.util;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

public class CypressResponseErrorHandler extends DefaultResponseErrorHandler implements ResponseErrorHandler {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return super.hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(response.getBody(), writer);
		logger.error("Issue with Cypress. Error code was "+ response.getRawStatusCode() + " " + response.getStatusText() +" with Response body: " + writer.toString());
		super.handleError(response);
	}

}
