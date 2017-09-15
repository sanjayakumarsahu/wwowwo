package com.geaviation.eds.service.events.app.impl;

import static com.geaviation.eds.service.events.app.util.EventsAppUtil.isCollectionNotEmpty;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.common.util.DataServiceUtils;
import com.geaviation.eds.service.events.app.api.IEventsMaintenanceApp;
import com.geaviation.eds.service.events.app.constants.EventsAppConstants;
import com.geaviation.eds.service.events.app.util.EventsAppUtil;
import com.geaviation.eds.service.events.data.api.IEventsMaintenanceData;
import com.geaviation.eds.service.events.model.maintenance.EngineUtilizationHistoryResponse;
import com.geaviation.eds.service.events.model.maintenance.MaintenanceData;
import com.geaviation.eds.service.events.model.maintenance.MaintenanceEventResponse;
import com.geaviation.eds.service.events.model.maintenance.MaintenanceReq;
import com.geaviation.eds.service.events.model.maintenance.RDOMaintenanceReq;
import com.geaviation.eds.service.events.model.maintenance.WaterWashMaintEventResponse;
import com.geaviation.eds.service.events.model.maintenance.WaterWashMaintenanceData;
import com.geaviation.eds.service.events.model.shopinfo.EventSortField;
import com.geaviation.eds.service.master.data.impl.caching.CacheConstants;
import com.geaviation.eds.service.master.data.impl.caching.CacheCreation;
import com.geaviation.eds.service.master.data.impl.caching.CacheUtil;

@Component
public class EventsMaintenanceAppImpl implements IEventsMaintenanceApp {

	private static final Log LOGGER = LogFactory.getLog(EventsMaintenanceAppImpl.class);
	@Autowired
	private IEventsMaintenanceData maintEventDataIntf;

	@Override
	public MaintenanceEventResponse getMaintenanceDataV1(String eventid) throws DataServiceException {
	
		LOGGER.info("getMaintenanceDataV1 App Impl Layer: Begin");
		List<MaintenanceData> lstMaintenanceData = null;
		MaintenanceEventResponse mainEventResponse = new MaintenanceEventResponse();
		if (DataServiceUtils.isNotNullandEmpty(eventid)) {
			lstMaintenanceData = maintEventDataIntf.getMaintenanceData(eventid);
			if (lstMaintenanceData != null && !lstMaintenanceData.isEmpty()) {
				mainEventResponse.setStatus(DataServiceConstants.SUCCESS);
				mainEventResponse.setMaintanenceData(lstMaintenanceData);
				mainEventResponse.setTotalRecords(mainEventResponse.getMaintanenceData().size());
			} else {
				LOGGER.error("Exception occurred in getMaintenanceDataV1");
				throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
						DataServiceConstants.NO_DATA_FOUND_E1003, DataServiceConstants.ERROR_STATUS,
						EventsAppConstants.MAINTENANCE_EVENT_EMPTY_LIST,
						EventsAppConstants.MAINTENANCE_EVENT_EMPTY_LIST);
			}
		} else {
			LOGGER.error(EventsAppConstants.MISSING_EVENT_ID);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.MISSING_EVENT_ID, EventsAppConstants.MISSING_EVENT_ID);
		}
		
		LOGGER.info("getMaintenanceDataV1 App Impl Layer: End");
		return mainEventResponse;

	}

	@SuppressWarnings("unchecked")
	@Override
	public MaintenanceEventResponse getMaintenanceDataV2(String eventid,
			String offset, String strLimit, EventSortField sortField) throws DataServiceException
	{
	
		LOGGER.info(" getMaintenanceDataV2 App Impl Layer: Begin");
		
		if(sortField!=null)
		{
		LOGGER.info("Input Params MaintenanceData: "+ eventid +"offset:" +offset
		+ "Limit:"+strLimit +"EventSortField:"+sortField.getFieldName()+" "+ sortField.getSortOrder());
		}

		MaintenanceEventResponse mainEventResponse =null;
		MaintenanceEventResponse mainEventFilterResponse = new MaintenanceEventResponse();
		List<MaintenanceData> lstMaintenanceData = null;    	

		if(DataServiceUtils.isNullandEmpty(eventid)) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS, DataServiceConstants.MISSING_INPUT_MSG, DataServiceConstants.MISSING_INPUT_MSG);
		}
		if(DataServiceUtils.isNullandEmpty(offset)){
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS, DataServiceConstants.OFFSET_MISSING, DataServiceConstants.OFFSET_MISSING);
		}
		if(DataServiceUtils.isNullandEmpty(strLimit)){
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS, DataServiceConstants.LIMIT_MISSING, DataServiceConstants.LIMIT_MISSING);
		}    	

		if(Integer.parseInt(strLimit)>5000 ){
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.DATA_VALIDATION_ERROR_E1002, DataServiceConstants.ERROR_STATUS, DataServiceConstants.LIMIT_FAILED_MSG, DataServiceConstants.LIMIT_FAILED_MSG);
		}


		String eventsCacheName = CacheConstants.EVENTS_CACHE_NAME;
		String key = eventid;

		Integer start = 0;
		Integer limit = 0;

		start = Integer.parseInt(offset); 				
		limit = Integer.parseInt(strLimit);


		List<Object> eventDetailsLst = getMaintenanceAllData(key,eventsCacheName,eventid);

		if(isCollectionNotEmpty(eventDetailsLst)){

			Object cachedObj = eventDetailsLst.get(0);
			if(cachedObj instanceof MaintenanceEventResponse)
			{				
				mainEventResponse = (MaintenanceEventResponse)eventDetailsLst.get(0);
			}
			else{
				CacheUtil.removeEventsCache(eventsCacheName,key);
				mainEventResponse = getMaintenanceData(eventid);
			}
		}

		if(mainEventResponse!=null && !mainEventResponse.getMaintanenceData().isEmpty()){
			mainEventFilterResponse.setTotalRecords(mainEventResponse.getMaintanenceData().size());
			mainEventFilterResponse.setStatus(mainEventResponse.getStatus());
			lstMaintenanceData = mainEventResponse.getMaintanenceData();
		}

		if(lstMaintenanceData!=null)
		{
			if(sortField!=null)
			{
				List<EventSortField> sortFieldlst = new ArrayList<EventSortField>();
				sortFieldlst.add(sortField);
				EventsAppUtil.sortList(lstMaintenanceData, sortFieldlst);
			}
			lstMaintenanceData =  EventsAppUtil.filterResultListV3(lstMaintenanceData,start,limit);
		}
		mainEventFilterResponse.setMaintanenceData(lstMaintenanceData);

		LOGGER.info(" getMaintenanceDataV2 App Impl Layer: End");
		return mainEventFilterResponse;


	}

	/**
	 * water wash events request service - to check if the all water wash events details are available in the cache
	 * @param key
	 * @param cacheName
	 * @param eventid
	 * @return - List<Object>
	 * @throws DataServiceException
	 */		
	public List<Object> getMaintenanceAllData(final String key, final String cacheName, final String eventid) throws DataServiceException {
		return CacheUtil.getListFromCache(cacheName, key, new CacheCreation<Object>() {
			@Override
			public List<Object> getAll() throws DataServiceException{
				List<Object> list = new ArrayList<Object>();
				MaintenanceEventResponse eventResponse = getMaintenanceData(eventid);
				if(null!= eventResponse){
					list.add(eventResponse);
				}
				return list;
			}
		});
	}

	/**
	 * Returns Engine Maintenance Event data
	 *
	 * @param Eventid
	 * @return List of Engine Maintenance Event Details
	 * @throws DataServiceException
	 */

	public MaintenanceEventResponse getMaintenanceData(String eventid)throws DataServiceException{

		List<MaintenanceData> lstMaintenanceData = null;
		MaintenanceEventResponse mainEventResponse = new MaintenanceEventResponse();

		lstMaintenanceData = maintEventDataIntf.getMaintenanceData(eventid);		
		mainEventResponse.setStatus(DataServiceConstants.SUCCESS);
		mainEventResponse.setMaintanenceData(lstMaintenanceData);

		return mainEventResponse;
	}

	/**
	 * Returns Engine Maintenance Event data
	 *
	 * @param esn
	 *  @param eventStartDate
	 *  @param eventEndDate
	 * @return WaterWashMaintEventResponse Details
	 * @throws DataServiceException
	 * @throws ParseException
	 */
	@Override
	public WaterWashMaintEventResponse getMaintenanceDataV1(RDOMaintenanceReq rdoMaintenanceReq) throws DataServiceException {

		LOGGER.info(" getMaintenanceDataV1 App Impl Layer: Begin");
		WaterWashMaintEventResponse mainEventResponse = null;

		if (DataServiceUtils.isNullandEmpty(rdoMaintenanceReq.getEventid())) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.EVETN_ID_MISSING, EventsAppConstants.EVETN_ID_MISSING);
		}

		if (!DataServiceUtils.isCollectionNotEmpty(rdoMaintenanceReq.getEsn())) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.ESN_MISSING, EventsAppConstants.ESN_MISSING);
		}
		if (DataServiceUtils.isNullandEmpty(rdoMaintenanceReq.getEventStartDate())) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.EVENT_START_DATE_MISSING, EventsAppConstants.EVENT_START_DATE_MISSING);
		}
		if (DataServiceUtils.isNullandEmpty(rdoMaintenanceReq.getEventEndDate())) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.EVENT_END_DATE_MISSING, EventsAppConstants.EVENT_END_DATE_MISSING);
		}
		mainEventResponse = getMaintenanceData(rdoMaintenanceReq);
		return mainEventResponse;

	}
	
	/**
	 * Returns Engine Maintenance Event data
	 *
	 * @param esn
	 *  @param eventStartDate
	 *  @param eventEndDate
	 * @return WaterWashMaintEventResponse Details
	 * @throws DataServiceException
	 * @throws ParseException
	 */

	public WaterWashMaintEventResponse getMaintenanceData(RDOMaintenanceReq rdoMaintenanceReq) throws DataServiceException {

		List<WaterWashMaintenanceData> lstMaintenanceData = null;
		WaterWashMaintEventResponse mainEventResponse = new WaterWashMaintEventResponse();

		lstMaintenanceData = maintEventDataIntf.getRDOMaintenanceData(rdoMaintenanceReq);
		mainEventResponse.setTotalRecords(lstMaintenanceData != null ? lstMaintenanceData.size() : 0);
		mainEventResponse.setStatus(DataServiceConstants.SUCCESS);
		mainEventResponse.setMaintanenceData(lstMaintenanceData);

		return mainEventResponse;
	}

	/**
	 * Returns Engine Maintenance Event data
	 *
	 * @param esn
	 *  @param eventStartDate
	 *  @param eventEndDate
	 * @return WaterWashMaintEventResponse Details
	 * @throws DataServiceException
	 * @throws ParseException
	 */

	public WaterWashMaintEventResponse getWashEventData(MaintenanceReq maintenanceReq)
			throws DataServiceException {

		List<WaterWashMaintenanceData> lstMaintenanceData = null;
		WaterWashMaintEventResponse mainEventResponse = new WaterWashMaintEventResponse();
		lstMaintenanceData = maintEventDataIntf.getFDMMaintenanceData(maintenanceReq);
		mainEventResponse.setTotalRecords(lstMaintenanceData != null ? lstMaintenanceData.size() : 0);
		mainEventResponse.setStatus(DataServiceConstants.SUCCESS);
		mainEventResponse.setMaintanenceData(lstMaintenanceData);

		return mainEventResponse;
	}

	@Override
	public WaterWashMaintEventResponse getWashEventsDataV1(MaintenanceReq maintenanceReq) throws DataServiceException {

		LOGGER.info(" getWashEventsData App Impl Layer: Begin");
		
		
		List<String> esnList =maintenanceReq.getEsn();			
		WaterWashMaintEventResponse mainEventResponse = null;
		if (!DataServiceUtils.isCollectionNotEmpty(esnList)) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.ESN_MISSING, EventsAppConstants.ESN_MISSING);
		}
		if (DataServiceUtils.isNullandEmpty(maintenanceReq.getEventStartDate())) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.EVENT_START_DATE_MISSING, EventsAppConstants.EVENT_START_DATE_MISSING);
		}
		if (DataServiceUtils.isNullandEmpty(maintenanceReq.getEventEndDate())) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.EVENT_END_DATE_MISSING, EventsAppConstants.EVENT_END_DATE_MISSING);
		}
		mainEventResponse = getWashEventData(maintenanceReq);
		LOGGER.info(" getMaintenanceDataV2 App Impl Layer: End");
		return mainEventResponse;

	}
	
	/**
	 * Returns Engine Maintenance Event data
	 *
	 * @param esn
	 *  @param eventStartDate
	 *  @param eventEndDate
	 * @return List of EngineUtilizationHistoryResponse Details
	 * @throws DataServiceException
	 * @throws ParseException
	 */

	public List<EngineUtilizationHistoryResponse>  getEngineUtilizationDetails(MaintenanceReq maintenanceReq)
			throws DataServiceException {

		List<EngineUtilizationHistoryResponse> engineUtilizationDetails = null;
		engineUtilizationDetails = maintEventDataIntf.getEngineUtilizationHistory(maintenanceReq);

		return engineUtilizationDetails;
	}

	public List<EngineUtilizationHistoryResponse> getEngineUtilizationHistory(MaintenanceReq maintenanceReq) throws DataServiceException{
		LOGGER.info(" getShopEventDetailsv1 App Impl Layer: Begin");
		List<String> esnList=maintenanceReq.getEsn();
		if (!DataServiceUtils.isCollectionNotEmpty(esnList)) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.ESN_MISSING, EventsAppConstants.ESN_MISSING);
		}
		if (DataServiceUtils.isNullandEmpty(maintenanceReq.getEventStartDate())) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.EVENT_START_DATE_MISSING, EventsAppConstants.EVENT_START_DATE_MISSING);
		}
		if (DataServiceUtils.isNullandEmpty(maintenanceReq.getEventEndDate())) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.EVENT_END_DATE_MISSING, EventsAppConstants.EVENT_END_DATE_MISSING);
		}
		List<EngineUtilizationHistoryResponse> shopVisitEventResponses  = getEngineUtilizationDetails(maintenanceReq);
		LOGGER.info(" getShopEventDetailsv1 App Impl Layer: End");
		return shopVisitEventResponses;
	}
	
	@Override
	public WaterWashMaintEventResponse getShopvisitOverhaulData(MaintenanceReq maintenanceReq) throws DataServiceException {

		LOGGER.info(" getShopvisitOverhaulData App Impl Layer: Begin");
		
		
		List<String> esnList =maintenanceReq.getEsn();			
		WaterWashMaintEventResponse mainEventResponse = null;
		if (!DataServiceUtils.isCollectionNotEmpty(esnList)) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.ESN_MISSING, EventsAppConstants.ESN_MISSING);
		}
		else if (DataServiceUtils.isNullandEmpty(maintenanceReq.getEventStartDate())) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.EVENT_START_DATE_MISSING, EventsAppConstants.EVENT_START_DATE_MISSING);
		}
		else if (DataServiceUtils.isNullandEmpty(maintenanceReq.getEventEndDate())) {
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.EVENT_END_DATE_MISSING, EventsAppConstants.EVENT_END_DATE_MISSING);
		}
		mainEventResponse = shopvisitOverhaulData(maintenanceReq);
		LOGGER.info("getShopvisitOverhaulData App Impl Layer: End");
		return mainEventResponse;

	}
	
	public WaterWashMaintEventResponse shopvisitOverhaulData(MaintenanceReq maintenanceReq)
			throws DataServiceException {

		List<WaterWashMaintenanceData> lstMaintenanceData = null;
		WaterWashMaintEventResponse mainEventResponse = new WaterWashMaintEventResponse();
		lstMaintenanceData = maintEventDataIntf.getShopvisitOverhaulData(maintenanceReq);
		
		if(lstMaintenanceData.isEmpty()){
			LOGGER.error("Exception occured in getShopvisitOverhaulData- "+EventsAppConstants.SHOPVISIT_EVENT_EMPTY_LIST);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.NO_DATA_FOUND_E1003,
					DataServiceConstants.ERROR_STATUS,EventsAppConstants.SHOPVISIT_EVENT_EMPTY_LIST,
					DataServiceConstants.COMMON_EXCEPTION_USER_MSG);
		}
		mainEventResponse.setTotalRecords(lstMaintenanceData.size());
		
		mainEventResponse.setStatus(DataServiceConstants.SUCCESS);
		mainEventResponse.setMaintanenceData(lstMaintenanceData);

		return mainEventResponse;
	}

}
