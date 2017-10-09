package com.geaviation.eds.service.common.oauth;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.common.util.DataServiceUtils;





public class OauthValidatorUtil {
	
	private static final Log LOGGER = LogFactory.getLog(OauthValidatorUtil.class);
	
		private OauthValidatorUtil(){
					
		}
	
		public static void validateURL(String url) throws DataServiceException{
			
			try {
				URL urlC =new URL(url);	
				LOGGER.info("OauthValidatorUtil: PING URL HOST:"+urlC.getHost());
			} catch (MalformedURLException e) {
				LOGGER.error("OauthValidatorUtil: PING URL HOST:"+DataServiceConstants.OAUTH_PING_ERROR);
				throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
						DataServiceConstants.ERROR_STATUS,DataServiceConstants.OAUTH_PING_ERROR,
						DataServiceConstants.OAUTH_PING_ERROR);
			} 
		}	
		
		public static void validateInputParameters(Map<String,String> oAuthMap) throws DataServiceException{
		
			if(DataServiceUtils.isNullandEmpty(oAuthMap.get(DataServiceConstants.CLIENT_ID)) ||			
			   DataServiceUtils.isNullandEmpty(oAuthMap.get(DataServiceConstants.CLIENT_SECRET))||
			   DataServiceUtils.isNullandEmpty(oAuthMap.get(DataServiceConstants.SCOPE))||
			   DataServiceUtils.isNullandEmpty(oAuthMap.get(DataServiceConstants.PING_URL))
			   ){
				
				LOGGER.info("OauthValidatorUtil : validateInputParameters:"+DataServiceConstants.OAUTH_INPUT_VALIDATION);
				throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
						DataServiceConstants.ERROR_STATUS,DataServiceConstants.OAUTH_INPUT_VALIDATION,
						DataServiceConstants.OAUTH_INPUT_VALIDATION);
			}
			validateURL(oAuthMap.get(DataServiceConstants.PING_URL));
		}
		
		
}

