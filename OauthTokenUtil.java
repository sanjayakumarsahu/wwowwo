package com.geaviation.eds.service.common.oauth;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.common.exception.DataServiceTechnicalException;

@SuppressWarnings("restriction")
@Component
public  class OauthTokenUtil  {
	
	@Autowired
	private OauthTokenClient oauthTokenGenerator;
	
	private static Map<String, OauthToken> oauthTokensMap= new HashMap<String, OauthToken>();
	
	private static final Log LOGGER = LogFactory.getLog(OauthTokenUtil.class);
	

	private static final String TOKEN_GRANT_TYPE_CLIENT_ID =  "grant_type=client_credentials&client_id=";
	private static final String TOKEN_CLIENT_SECRET = "&client_secret=" ;
	private static final String TOKEN_SCOPE="&scope=";
	
	
	private static final String CONTENT_TYPE="application/x-www-form-urlencoded";
	
	
	
	public OauthToken generateOauthToken(Map<String,String> oAuthMap) throws DataServiceException {
		
		LOGGER.info("OauthTokenUtil : generateOauthToken : Begin");
		
		OauthToken token=null;

		try{
			
			OauthValidatorUtil.validateInputParameters(oAuthMap);
			
			String oAuthkey= oAuthMap.get(DataServiceConstants.CLIENT_ID)+
							 oAuthMap.get(DataServiceConstants.CLIENT_SECRET)+
					         oAuthMap.get(DataServiceConstants.SCOPE);
					token =  getOauthAllData(oAuthkey,oAuthMap);
					LOGGER.info("OauthTokenUtil : generateOauthToken : "+token.getExpiresIn() +" TIMESTAMP "+token.getTimestamp());
		}catch(Exception exception) {
	        LOGGER.error("OauthTokenUtil : generateOauthToken : Exception Occured"+exception);

				throw exception;
		}
		
		LOGGER.info("OauthTokenUtil : generateOauthToken : END");
		return token;		
		
	}
	
	
	

	private String getPayLoad(Map<String,String> oAuthMap) throws DataServiceException{
		
		StringBuilder payLoad = new StringBuilder(TOKEN_GRANT_TYPE_CLIENT_ID);
		
		payLoad .append(oAuthMap.get(DataServiceConstants.CLIENT_ID))
				.append(TOKEN_CLIENT_SECRET).append(oAuthMap.get(DataServiceConstants.CLIENT_SECRET))
				.append(TOKEN_SCOPE).append(oAuthMap.get(DataServiceConstants.SCOPE));
		
		return payLoad.toString();
	}
	
		
	/**
     * Populates Token object using following JSON String
     * @param getAccessToken
     * @return
     */
    private OauthToken getAccessToken(String accessTokenJson) throws DataServiceException{
    	
    	LOGGER.info("OauthTokenUtil : getAccessToken : Begin");
    	
        JSONParser parser = new JSONParser();
        String timestamp = null;
        Calendar now = Calendar.getInstance();
        OauthToken token = new OauthToken();
        try {
            Object obj = parser.parse(accessTokenJson);
            JSONObject jsonObject = (JSONObject)obj;
            token.setAccessToken((String)jsonObject.get(DataServiceConstants.ACCESS_TOKEN));
            long expiresIn = Integer.parseInt(jsonObject.get(DataServiceConstants.EXPIRES_IN).toString());
            token.setExpiresIn(expiresIn);
            token.setRefreshToken((String)jsonObject.get(DataServiceConstants.REFRESH_TOKEN));
            token.setTokenType((String)jsonObject.get(DataServiceConstants.TOKEN_TYPE));
            String curDate =  Integer.toString(now.get(Calendar.MONTH) ) + "-" + Integer.toString(now.get(Calendar.DATE)) + "-"+ Integer.toString(now.get(Calendar.YEAR));
    	    String curTime =  Integer.toString(now.get(Calendar.HOUR_OF_DAY)) + "-" + Integer.toString(now.get(Calendar.MINUTE)) + "-" + Integer.toString(now.get(Calendar.SECOND)); 
    	    timestamp = curDate + "-" + curTime;
    	       	    
    	    token.setTimestamp(timestamp);
    	              
        } catch (Exception e) {
            
            throw new DataServiceTechnicalException("OauthTokenUtil : getAccessToken ParseException",e);
        } 
        LOGGER.info("OauthTokenUtil : getAccessToken : End");
        
        return token;

    }
    
    
    
    private OauthToken getOauthAllData(final String key, Map<String,String> oAuthMap) throws DataServiceException {
    	    	
    	if(null!=oauthTokensMap){
    		
    		if("Y".equalsIgnoreCase(oAuthMap.get(DataServiceConstants.REFRESH_TOKEN))){
    			oauthTokensMap.remove(key);
    		}
	    	if(oauthTokensMap.containsKey(key) && isTokenValid(oauthTokensMap.get(key))){
	    		return oauthTokensMap.get(key);
	    	}else{
	    		oauthTokensMap.put(key,getOauthToken(oAuthMap));
	    		return oauthTokensMap.get(key);
	    	}
    	}else{
    		oauthTokensMap =  new HashMap<String,OauthToken>();
    		oauthTokensMap.put(key,getOauthToken(oAuthMap));
    		return oauthTokensMap.get(key);
    	}
    	
	}
	
    private OauthToken getOauthToken(Map<String,String> oAuthMap) throws DataServiceException {
    	LOGGER.info("OauthTokenUtil: getOauthToken : Begin");
		
		OauthToken token=null;
		HttpResponse httpResponse = null;
		String response = null;
		
		try {
			
			
			httpResponse = oauthTokenGenerator.doPost(oAuthMap.get(DataServiceConstants.PING_URL),											
											 getPayLoad(oAuthMap),
											 CONTENT_TYPE);
			
			errorHandling(httpResponse);
			
			response = oauthTokenGenerator.getResponsePayload(httpResponse);
			token = getAccessToken(response);
			if (token != null) {
				LOGGER.info("OauthTokenUtil: getOauthToken : Token Received from Ping");
			} else {
				LOGGER.info("OauthTokenUtil: getOauthToken : Failed to get token from Ping");				
				throw new DataServiceTechnicalException("Failed to get token from Ping");
			}
			
			
		}catch(DataServiceException exception){
			throw exception;
		}catch(Exception exception) {
			LOGGER.info("OauthTokenUtil: getOauthToken Exception Occured:",exception);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
					DataServiceConstants.ERROR_STATUS,exception.getMessage(),
					DataServiceConstants.OAUTH_PING_ERROR);
		}finally{
			httpResponse = null;
			response = null;
		
		}
		LOGGER.info("OauthTokenUtil: getOauthToken : End");
		return token;
	}
    
    
    /**
	 * This method takes token as a parameter and validates for expiration time.
	 * @param token
	 * @return boolean
	 * @throws IOException
	 */
	private boolean isTokenValid(OauthToken token) throws DataServiceException {
		LOGGER.info("OauthTokenUtil: isTokenValid : Begin");
		long mins = 0;
		boolean isValid = false;
		long expiresIn = token.getExpiresIn();
		String timestamp = token.getTimestamp();
		
		Calendar orignalTimestamp = getOriginalTimestamp(timestamp);
		
		Calendar currentTimestamp = Calendar.getInstance();
		
	   
		long diffInSeconds = (currentTimestamp.getTimeInMillis() - orignalTimestamp.getTimeInMillis())/1000;
		
		// Deducting 200 seconds just to ensure that the token is not expired during sentry call.
		long seconds = expiresIn - 200;
			
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		LOGGER.info("ORIGINAL TIMESTAMP :"+dateFormat.format(orignalTimestamp.getTime()));
		LOGGER.info("CURRENT  TIMESTAMP :"+dateFormat.format(currentTimestamp.getTime()));

		if (diffInSeconds < seconds) {
			isValid = true;
			LOGGER.info("OauthTokenUtil: isTokenValid : Token validated and is found to be valid...");
			mins = (seconds - diffInSeconds)/60;
			LOGGER.info("OauthTokenUtil : diffInSeconds : "+diffInSeconds+" seconds:"+seconds);
			LOGGER.info("OauthTokenUtil: isTokenValid : Token will get expired in " + mins + " minutes");
		} else {
			LOGGER.info("OauthTokenUtil: isTokenValid : Token validated and is found to be in-valid...");
		}		
		LOGGER.info("OauthTokenUtil: isTokenValid : End");
		return isValid;
	}
	
	/**
	 * This method constructs and returns a calendar instance by taking timestamp as input.  
	 * @param timestamp
	 * @return Calendar
	 */
	private Calendar getOriginalTimestamp(String timestamp) {
		LOGGER.info("OauthTokenUtil: getOriginalTimestamp : Begin timestamp"+timestamp);
		Calendar originalTimestamp = Calendar.getInstance();
		StringTokenizer st = new StringTokenizer(timestamp, "-");
		int[] val = new int[st.countTokens()];
		int index = 0;
		while (st.hasMoreTokens()) {
			val[index] = Integer.parseInt(st.nextToken());
			index++;
		}
		originalTimestamp.set(val[2], val[0], val[1], val[3], val[4], val[5]);
		LOGGER.info("OauthTokenUtil: getOriginalTimestamp : End");
		return originalTimestamp;
	}
	
	private void errorHandling(HttpResponse httpResponse) throws DataServiceException{
		int errorCode = httpResponse.getStatusLine().getStatusCode();
		
		switch(errorCode){
		
		case 200:
				break;
		case 401:
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
					DataServiceConstants.ERROR_STATUS,DataServiceConstants.OAUTH_PING_INVALID_TOKEN_MSG,
					DataServiceConstants.OAUTH_PING_INVALID_TOKEN_UMSG);
			
		case 500:
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
					DataServiceConstants.ERROR_STATUS,DataServiceConstants.OAUTH_PING_INVALID_SERVER_DOWN_MSG,
					DataServiceConstants.OAUTH_PING_INVALID_SERVER_DOWN_UMSG);
			
		case 404:
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
					DataServiceConstants.ERROR_STATUS,DataServiceConstants.OAUTH_PING_INVALID_BACKEND_PATH_MSG,
					DataServiceConstants.OAUTH_PING_INVALID_BACKEND_PATH_UMSG);
		case 400:
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
					DataServiceConstants.ERROR_STATUS,""+httpResponse,
					DataServiceConstants.OAUTH_PING_INPUTPARAMS_ERROR);
		default:
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
					DataServiceConstants.ERROR_STATUS,""+httpResponse,
					DataServiceConstants.OAUTH_PING_ERROR);
		}
		
		
	}

}
