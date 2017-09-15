package com.geaviation.eds.service.events.app.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.common.oauth.OauthTokenUtil;
import com.geaviation.eds.service.events.model.diagdds.OperatorProperties;

@Component
public class EventsDiagddsRestClient {
	
	@Autowired
	@Qualifier("operatorProperties")
	private OperatorProperties operatorProperties;
	
	@Autowired
	private OauthTokenUtil oauthTokenUtil;

	private static final Log LOGGER = LogFactory.getLog(EventsDiagddsRestClient.class);
	
	public <T> List<T> getOperatorListResponse(String url, Map<String, String> parameters, String type, Class<T> clazz, String ssoId, String portalId) throws DataServiceException {

		
		LOGGER.info("getOperatorListResponse :: EventsDiagddsRestClient App Impl Layer: Begin");
		int httpstatus;
		String bodyAsString=null;
		String mediaType = null;
		mediaType = type;
		BufferedReader br;
		StringBuilder sb;
		String line;
		try {
			String formattedURL = buildURL(url, parameters);
			
			URL finalURL = new URL(formattedURL);
			HttpURLConnection conn = (HttpURLConnection) finalURL.openConnection();
			conn.setRequestProperty(DataServiceConstants.SM_SSOID, ssoId);
			conn.setRequestProperty(DataServiceConstants.PORTAL_ID, portalId);
			conn.setRequestMethod("GET");
			
			httpstatus = conn.getResponseCode();
						
			if(httpstatus == 401) { 
				/*If we get unauthorized error, try again with refresh token*/
				Map<String,String> oAuthMap = new HashMap<String, String>();
				oAuthMap.put(DataServiceConstants.CLIENT_ID, operatorProperties.getOperatorClientId());
				oAuthMap.put(DataServiceConstants.CLIENT_SECRET, operatorProperties.getOperatorClientSecret());
				oAuthMap.put(DataServiceConstants.SCOPE,operatorProperties.getOperatorScope());
				oAuthMap.put(DataServiceConstants.PING_URL,operatorProperties.getOperatorPingUrl());
				oAuthMap.put(DataServiceConstants.REFRESH_TOKEN,"Y");
				String token = oauthTokenUtil.generateOauthToken(oAuthMap).getAccessToken();
				parameters.put(operatorProperties.getOperatorTokenKey(), token);
				return getOperatorListResponse(url, parameters, mediaType, clazz, ssoId, portalId);
			}
			else if ( httpstatus != 200) {
				Response.Status status;
				status = Response.Status.fromStatusCode(httpstatus);
				LOGGER.error("getOperatorOrgs Execution - Statuscode:"+status.getStatusCode()+" Message: "+bodyAsString);
				throw new DataServiceException(String.valueOf(status.getStatusCode()),bodyAsString);
			}
			
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			
			ObjectMapper objectMapper = new  ObjectMapper();
			LOGGER.info("getOperatorListResponse :: EventsDiagddsRestClient App Impl Layer: End");
			return  objectMapper.readValue(sb.toString(),objectMapper.getTypeFactory().constructCollectionType(
					List.class, clazz));
		}
		catch(Exception e) {
			LOGGER.error("Exception while executing getOperatorOrgs service in EventDiagddsRestClient : "+e);
			throw new DataServiceException("Cannot retrieve data.", e);
		}
	}

	

	private String buildURL(String url, Map<String, String> parameters) throws DataServiceException{
		EventsURLBuilder builder = new EventsURLBuilder();
		builder.addPath(url);
		for(String key: parameters.keySet()) {
			if(key != null && !key.isEmpty() && parameters.get(key) != null && !parameters.get(key).isEmpty()) {
				builder.addParam(key, parameters.get(key));
			}			
		}

		return builder.build();
	}
}
