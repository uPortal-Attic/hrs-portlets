package edu.wisc.portlet.hrs.util;

import javax.portlet.ResourceResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HrsDownloadControllerUtils {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static void setResponseHeaderForDownload(ResourceResponse response, String downloadFilename, String extension) {
		response.setProperty("Content-Disposition", "attachment; filename=\""+downloadFilename+"."+StringUtils.upperCase(extension)+"\"");
	}
}
