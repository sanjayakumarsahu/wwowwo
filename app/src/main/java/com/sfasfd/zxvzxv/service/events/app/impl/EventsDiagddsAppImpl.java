package com.geaviation.eds.service.events.app.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.common.exception.DataServiceTechnicalException;
import com.geaviation.eds.service.common.oauth.OauthTokenUtil;
import com.geaviation.eds.service.common.util.DataServiceUtils;
import com.geaviation.eds.service.common.util.DateFormatUtil;
import com.geaviation.eds.service.events.app.api.IEventsDiagdssApp;
import com.geaviation.eds.service.events.app.constants.EventsAppConstants;
import com.geaviation.eds.service.events.data.api.IEventsDiagddsData;
import com.geaviation.eds.service.events.model.diagdds.CnrDO;
import com.geaviation.eds.service.events.model.diagdds.CnrListBO;
import com.geaviation.eds.service.events.model.diagdds.CnrResponse;
import com.geaviation.eds.service.events.model.diagdds.EsnEventResponse;
import com.geaviation.eds.service.events.model.diagdds.FaultExceedResponse;
import com.geaviation.eds.service.events.model.diagdds.FaultExceedanceDO;
import com.geaviation.eds.service.events.model.diagdds.NDRDO;
import com.geaviation.eds.service.events.model.diagdds.NDRResponse;
import com.geaviation.eds.service.events.model.diagdds.OperatorProperties;
import com.geaviation.eds.service.events.model.diagdds.Org;


@Component
public class EventsDiagddsAppImpl implements IEventsDiagdssApp {

	private static final Log LOGGER = LogFactory.getLog(EventsDiagddsAppImpl.class);
	
	@Autowired
	private IEventsDiagddsData eventsDiagddsDataIntf;
	
	@Autowired
	@Qualifier("operatorProperties")
	private OperatorProperties operatorProperties;
	
	@Autowired
	private EventsDiagddsRestClient restClient;
	
	@Autowired
	private OauthTokenUtil oauthTokenUtil;

	@Override
	public FaultExceedResponse getFaultExceedanceDS(
			Map<String, String> map) throws DataServiceException {
		
		 LOGGER.info(" getFaultExceedanceDS App IMPL-Begin");
		 LOGGER.info("Input Prams: engineFamily,startDate,endDate,fleetId "+map);
		    String engineFamily = "";
			String fleetId = "";
			String startDate = "";
			String endDate = "";	
			FaultExceedResponse faultExceedanceBO = new FaultExceedResponse();
		
				if (null != map && map.size() > 0) {
	 				engineFamily = map.get(EventsAppConstants.ENGINE_FAMILY);
	 				fleetId = map.get(EventsAppConstants.FLEET_ID);
	 				startDate = map.get(EventsAppConstants.START_DATE);
	 				endDate = map.get(EventsAppConstants.END_DATE);
				}
				
				
				if(DataServiceUtils.isNotNullandEmpty(engineFamily) && DataServiceUtils.isNotNullandEmpty(fleetId) 
						&& DataServiceUtils.isNotNullandEmpty(startDate) && DataServiceUtils.isNotNullandEmpty(endDate) ){
				List<FaultExceedanceDO> lstFaultExceedanceDOs = eventsDiagddsDataIntf
						.getFaultExceedanceDS(engineFamily, fleetId, startDate,endDate);
				
				if (null != lstFaultExceedanceDOs && !lstFaultExceedanceDOs.isEmpty()) {
					faultExceedanceBO.setFaultExceedanceDO(lstFaultExceedanceDOs);
					faultExceedanceBO.setSuccess("true");
				}
				
				 else  {
					 LOGGER.info("NON DATA FOUND FOR getFaultExceedanceDS APP IMPL");
					throw new DataServiceException(
							DataServiceConstants.ERROR_TYP_ERR,
						    DataServiceConstants.MISSING_INPUT_ERROR_E1001,
							DataServiceConstants.ERROR_STATUS,
					     	EventsAppConstants.UI_MESSAGE,
							EventsAppConstants.DESC_MESSAGE_DATA);
				}
				}else if (DataServiceUtils.isNullandEmpty(engineFamily) || DataServiceUtils.isNullandEmpty(fleetId) 
						|| DataServiceUtils.isNullandEmpty(startDate) || DataServiceUtils.isNullandEmpty(endDate)) {
				 throw new DataServiceException(
						 DataServiceConstants.ERROR_TYP_ERR,
						 DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
						 DataServiceConstants.ERROR_STATUS,
						 EventsAppConstants.UI_MESSAGE,
						 EventsAppConstants.DESC_MESSAGE_INPUTS);
			}
		
			LOGGER.info(" getFaultExceedanceDS App IMPL-END");
	         return faultExceedanceBO;
	    }

	@Override
	public NDRResponse getNDRDataDS(Map<String, String> map)
			throws DataServiceException {
		    NDRResponse ndrListDO = new NDRResponse();
		    LOGGER.info("getNDRDataDS App IMPL-Begin");
		    LOGGER.info("Input Prams: engineFamily,fleetId "+map);
			String engineFamily = "";
			String fleetId = "";

			Map<String, String> parameters = new HashMap<String, String>();
			for (Map.Entry<String, String> e : map.entrySet()) {

				parameters.put(e.getKey(), e.getValue());

			}
			List<String> paramList = new ArrayList<String>();
			paramList.add(EventsAppConstants.FLEET_ID);
			paramList.add(EventsAppConstants.ENGINE_FAMILY);
			
			if (null != map && map.size() > 0) {
 				engineFamily = map.get(EventsAppConstants.ENGINE_FAMILY);
 				fleetId = map.get(EventsAppConstants.FLEET_ID);
			}
			
			if (DataServiceUtils.isNotNullandEmpty(engineFamily)
					&& DataServiceUtils.isNotNullandEmpty(fleetId)) {
			List<NDRDO> ndrDOList = eventsDiagddsDataIntf.getNDRDataDS(paramList,
					parameters);
			if (null != ndrDOList && !ndrDOList.isEmpty()) {
				ndrListDO.setNdrDO(ndrDOList);
				ndrListDO.setSuccess("true");
			} else {
				throw new DataServiceException(
						DataServiceConstants.ERROR_TYP_ERR,
						DataServiceConstants.MISSING_INPUT_ERROR_E1001,
						DataServiceConstants.ERROR_STATUS,
						EventsAppConstants.UI_MESSAGE,
						EventsAppConstants.DESC_MESSAGE_DATA);
			  }
			}else {
					 throw new DataServiceException(
							 DataServiceConstants.ERROR_TYP_ERR,
							 DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
							 DataServiceConstants.ERROR_STATUS,
							 EventsAppConstants.UI_MESSAGE,
							 EventsAppConstants.DESC_MESSAGE_INPUTS);
					}


			LOGGER.info(" getNDRDataDS App IMPL-END");
		return ndrListDO;
	}
	
	@Override
	 	public CnrResponse getCNRsDS(Map<String, String> map)
	 			throws DataServiceException {
	 		
	 		LOGGER.info("getCNRsDS App Layer: Begin");
	 		LOGGER.info("Input Prams: " + map);
	 		
	 		CnrListBO cnrlist = null;
	 		CnrResponse cnrResponse = null;
	 		try {
	 			String startDate = null; 
	 			String endDate = null;	
	 			boolean isInputExist = false;
	 			List<String> paramList = new ArrayList<String>();
	 			Map<String, String> parameters = new HashMap<String, String>();
	 			
	 			for (Entry<String, String> e : map.entrySet()) {
	 				
	 				parameters.put(e.getKey(), e.getValue());
	 				if(DataServiceUtils.isNotNullandEmpty(map.get(e.getKey()))){
	 					isInputExist = true;
	 					paramList.add(e.getKey());
	 			}
	 			}
	 			if (null != map && map.size() > 0) {
	 				startDate = map.get("startDate");
	  				endDate = map.get("endDate");
	 			}
	 			 if(!isInputExist) {					
	 				 throw new DataServiceException(
	 						 DataServiceConstants.ERROR_TYP_ERR,
	 						 DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
	 						 DataServiceConstants.ERROR_STATUS,
	 						 EventsAppConstants.UI_MESSAGE_NO_INPUTS,
	 						 EventsAppConstants.DESC_MESSAGE_INPUTS);
	 				}
	 			 else if ((DataServiceUtils.isNotNullandEmpty(startDate) && DataServiceUtils.isNullandEmpty(endDate)) 
	 				|| (DataServiceUtils.isNullandEmpty(startDate) && DataServiceUtils.isNotNullandEmpty(endDate))) { 
	 				
	 				 throw new DataServiceException(
	 						 DataServiceConstants.ERROR_TYP_ERR,
	 						 DataServiceConstants.NO_DATA_FOUND_E1003,
	 						 DataServiceConstants.ERROR_STATUS,
	 						 EventsAppConstants.UI_MESSAGE,
	 						 EventsAppConstants.DESC_MESSAGE_DATE_INPUTS);
	 			} 
	 			List<CnrDO> cnrDOList = eventsDiagddsDataIntf.getCNRsDS(paramList,
	 					parameters);
	 			if (!cnrDOList.isEmpty()) {
	 				cnrlist = new CnrListBO();
	 				cnrResponse = new CnrResponse();
	 				cnrlist.setCnrs(cnrDOList);
	 				cnrlist.setCnrCount(cnrDOList.size());
	 				cnrlist.setSuccess(true);
	 				cnrResponse.setCnrListBO(cnrlist);
	 			} else {
	 				throw new DataServiceException(
	 						DataServiceConstants.ERROR_TYP_ERR,
	 						DataServiceConstants.MISSING_INPUT_ERROR_E1001,
	 						DataServiceConstants.ERROR_STATUS,
	 						EventsAppConstants.UI_MESSAGE,
	 						EventsAppConstants.DESC_MESSAGE_DATA);
	 			}
	 
	 		} catch (Exception ex) {
	 			throw new DataServiceTechnicalException(DataServiceConstants.DB_ERROR, ex);
	 		}
	 		LOGGER.info("getCNRsDS App Layer: End");
	 		return cnrResponse;
	 	}
	
	@Override
	public EsnEventResponse getEventsLineGivenEsn(String esn,
			long timePeriodStart, long timePeriodEnd, String src, String phase) throws DataServiceException {
	
	LOGGER.info("getEventsLineGivenEsn App Impl Layer: Begin");
	LOGGER.info("Input Params esn:" + esn + "timePeriodstart:" + timePeriodStart + "timePeriodend:" + timePeriodEnd + "src:" + src + "phase:" + phase);
	EsnEventResponse esnEventResponse = new EsnEventResponse();

	try{
	if(DataServiceUtils.isNullandEmpty(esn) || DataServiceUtils.isNullandEmpty(src)) {
			LOGGER.error("Exception occured in getEventsLineGivenEsn -" + EventsAppConstants.ESN_SRC_TIMEPERIOD_MISSING);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					DataServiceConstants.MISSING_INPUT_MSG, EventsAppConstants.ESN_SRC_TIMEPERIOD_MISSING);
		}
	
	if (timePeriodStart>timePeriodEnd){
		LOGGER.error(EventsAppConstants.EVENTS_INVALID_TIMEPERIOD_ENDDATE);
		throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.DATA_VALIDATION_ERROR_E1002, DataServiceConstants.ERROR_STATUS,
				EventsAppConstants.EVENTS_INVALID_TIMEPERIOD_ENDDATE, EventsAppConstants.EVENTS_INVALID_TIMEPERIOD_ENDDATE);
	}
	
	Date timePeriodStartDt = new Date(timePeriodStart);
	Date timePeriodEndDt = new Date(timePeriodEnd);
	String timePeriodStartStr = DateFormatUtil.formatDateTime(timePeriodStartDt);
	String timePeriodEndStr =DateFormatUtil.formatDateTime(timePeriodEndDt);
	
	esnEventResponse = eventsDiagddsDataIntf.getEventData(esn, timePeriodStartStr, timePeriodEndStr, src, phase);

	if (!DataServiceUtils.isCollectionNotEmpty(esnEventResponse.getEventDetails())) {
		LOGGER.info("Exception occured in getEventsLineGivenEsn-"
				+ EventsAppConstants.ERROR_MSG_EVENTS_OVERLAY_EMPTY_LIST);
		throw new DataServiceException(
				DataServiceConstants.ERROR_TYP_ERR,
				DataServiceConstants.NO_DATA_FOUND_E1003,
				DataServiceConstants.ERROR_STATUS,
				EventsAppConstants.ERROR_MSG_EVENTS_OVERLAY_EMPTY_LIST,
				EventsAppConstants.ERROR_MSG_EVENTS_OVERLAY_EMPTY_LIST);
	}else{
		esnEventResponse.setSuccess(DataServiceConstants.STATUS);
	}
	} catch (Exception ex) {
			throw new DataServiceTechnicalException(DataServiceConstants.DB_ERROR, ex);
		}
		LOGGER.info("getEventsLineGivenEsn App Layer: End");
		return esnEventResponse;
	}
	
	
	@Override
	public Set<Org> getOperatorOrgs(String ssoId, String portalId) throws DataServiceException {
		LOGGER.info("getOperatorOrgs App Impl Layer: Begin");
		LOGGER.info("Input Params SSO: " + ssoId + " Portal id: " + portalId);
		int count=0;
		Set<Org> orgList = new TreeSet<Org>();
		
		if(!DataServiceUtils.isNotNullandEmpty(ssoId) || !DataServiceUtils.isNotNullandEmpty(portalId)){
			LOGGER.info("getOperatorOrgs :: ssoId and portalId is mandatory");
			throw new DataServiceException(DataServiceConstants.ERROR_STATUS, DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS, DataServiceConstants.MISSING_INPUT_MSG, DataServiceConstants.SSO_PORTAL_ID_MANDATORY);
		}
		
		List<String> portalList = new ArrayList<String>(Arrays.asList(portalId.split("\\|")));
		for (String portal : portalList) {
			LOGGER.info("Checking portal by portal ==> "+portal);
			try {
				orgList.addAll(restClient.getOperatorListResponse(operatorProperties.getOperatorUrl(), getParameters(), MediaType.APPLICATION_JSON, Org.class, ssoId, portal));				
			} catch (Exception e) {
				LOGGER.info("Exception in getOperatorOrgs UtilityOperatorAppImpl " +e);
				count++;
				if(count == portalList.size())
			
				throw new DataServiceException(
						"Forbidden",
						DataServiceConstants.UNAUTHORIZED_E1006,
						DataServiceConstants.ERROR_STATUS,
						"Access Denied",
						"User Does not have access");
			}
		}
		LOGGER.info("getOperatorOrgs App Layer: End");
		return orgList;
	}
	
	/**
	 * 
	 * @return
	 * @throws DataServiceException
	 */
	private Map<String, String> getParameters() throws DataServiceException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(operatorProperties.getOperatorTokenKey(), getOAuthToken());
		return parameters;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws DataServiceException
	 */
	private String getOAuthToken() throws DataServiceException  {
		Map<String,String> oAuthMap = new HashMap<String, String>();
		oAuthMap.put(DataServiceConstants.CLIENT_ID, operatorProperties.getOperatorClientId());
		oAuthMap.put(DataServiceConstants.CLIENT_SECRET,operatorProperties.getOperatorClientSecret());
		oAuthMap.put(DataServiceConstants.SCOPE,operatorProperties.getOperatorScope());
		oAuthMap.put(DataServiceConstants.PING_URL,operatorProperties.getOperatorPingUrl());
		return oauthTokenUtil.generateOauthToken(oAuthMap).getAccessToken();
	}

	
	
}

