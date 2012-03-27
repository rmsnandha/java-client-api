package com.marklogic.client.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marklogic.client.QueryOptionsManager;
import com.marklogic.client.config.search.QueryOptions;
import com.marklogic.client.io.QueryOptionsHandle;
import com.marklogic.client.io.marker.QueryOptionsReadHandle;
import com.marklogic.client.io.marker.QueryOptionsWriteHandle;

public class QueryOptionsManagerImpl implements QueryOptionsManager {


	static final private Logger logger = LoggerFactory.getLogger(AbstractDocumentImpl.class);

	private String QUERY_OPTIONS_BASE = "/config/search";
	private RESTServices services;
	
	public QueryOptionsManagerImpl(RESTServices services) {
		this.services = services;
	}
	


	@Override
	public void deleteOptions(String name) {
		services.deleteValue(QUERY_OPTIONS_BASE, name);
	}

	

	@Override
	public QueryOptions newOptions() {
		QueryOptionsHandle handle = new QueryOptionsHandle();
		return handle;
	}

	@Override
	public <T extends QueryOptionsReadHandle> T readOptions(String name, T searchOptionsHandle) {
		if (name == null)
			throw new IllegalArgumentException("Cannot read options for null name");

		searchOptionsHandle.receiveContent(services.getValue(QUERY_OPTIONS_BASE, name, "application/xml", searchOptionsHandle.receiveAs()));
		return searchOptionsHandle;
	}

	@Override
	public <T extends QueryOptionsWriteHandle> void writeOptions(String name,
			T searchOptionsHandle) {
		services.putValue(QUERY_OPTIONS_BASE, name, "application/xml", searchOptionsHandle.sendContent());
	}

}
