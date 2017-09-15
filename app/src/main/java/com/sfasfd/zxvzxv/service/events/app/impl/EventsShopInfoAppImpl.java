package com.geaviation.eds.service.events.app.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.common.util.DataServiceUtils;
import com.geaviation.eds.service.events.app.api.IEventsShopInfoApp;
import com.geaviation.eds.service.events.app.constants.EventsAppConstants;
import com.geaviation.eds.service.events.app.util.EventServiceValidator;
import com.geaviation.eds.service.events.data.api.IEventsShopInfoData;
import com.geaviation.eds.service.events.model.shopinfo.ShopEventDetailsResponse;
import com.geaviation.eds.service.events.model.shopinfo.ShopVisitEventDetailsResponse;
import com.geaviation.eds.service.events.model.shopinfo.ShopVisitEventRequest;
import com.geaviation.eds.service.events.model.shopinfo.ShopVisitEventResponse;

@Component
public class EventsShopInfoAppImpl implements IEventsShopInfoApp {

	private static final Log LOGGER = LogFactory
			.getLog(EventsShopInfoAppImpl.class);

	@Autowired
	private IEventsShopInfoData shopEventDataIntf;

	@Override
	public ShopVisitEventResponse getShopEventHistoryV2(String strEsn)
			throws DataServiceException {
		LOGGER.info("getShopEventHistoryV2 App Impl Layer: Begin");
		LOGGER.info("Input Params Esn "+ strEsn);
		
		ShopVisitEventResponse shopEventResponse = new ShopVisitEventResponse();
		EventServiceValidator.validate(EventServiceValidator.DATA_ELEMENT_ESN,
				strEsn);

		shopEventResponse = shopEventDataIntf.getShopEventHistoryV2(strEsn);

		if (!DataServiceUtils.isCollectionNotEmpty(shopEventResponse
				.getShopVisitEventHistory())) {
			LOGGER.info("Exception occured in getShopEventHistoryV2-"
					+ EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_EMPTY_LIST);
			throw new DataServiceException(
					DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.NO_DATA_FOUND_E1003,
					DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_EMPTY_LIST,
					EventsAppConstants.ERROR_UMSG_ENGINE_EVENT_HISTORY_EMPTY_LIST);
		}
		
		LOGGER.info("getShopEventHistoryV2 App Impl Layer: End");
		return shopEventResponse;
	}


	@Override
	public ShopVisitEventDetailsResponse getShopEventHistoryV3(ShopVisitEventRequest shopVisitEventRequest) throws DataServiceException {

		LOGGER.info("getShopEventHistoryV3 App Impl Layer: Begin");
		String eventFromDate = null;
		String eventToDate = null;
		eventFromDate = shopVisitEventRequest.getEventFromDate();
		eventToDate = shopVisitEventRequest.getEventToDate();
		LOGGER.info("Input Prams ShopVisitEventRequest "+shopVisitEventRequest.getEsn()+" "+ shopVisitEventRequest.getEngineModel()+" "+ eventFromDate
				+" "+eventToDate+" "+shopVisitEventRequest.getAirline()+" "+shopVisitEventRequest.getRemovalInd()
				+" "+shopVisitEventRequest.getShopInd()+" "+shopVisitEventRequest.getCause());
		
		ShopVisitEventDetailsResponse shopEventResponse = new ShopVisitEventDetailsResponse();

		if (DataServiceUtils.isNullandEmpty(shopVisitEventRequest.getEsn())
				&& (DataServiceUtils.isNullandEmpty(shopVisitEventRequest.getEngineModel())
						|| DataServiceUtils.isNullandEmpty(eventFromDate)
						|| DataServiceUtils.isNullandEmpty(eventToDate))) {

			LOGGER.info("Exception occured in getShopEventHistoryV3-"
					+ EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_MISSING_INPUT);
			throw new DataServiceException(
					DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001,
					DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_MISSING_INPUT,
					EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_MISSING_INPUT);
		}
		if (DataServiceUtils.isNotNullandEmpty(shopVisitEventRequest.getEsn())) {
			EventServiceValidator.validate(
					EventServiceValidator.DATA_ELEMENT_ESN, shopVisitEventRequest.getEsn());

		}
		if (DataServiceUtils.isNotNullandEmpty(eventFromDate) && DataServiceUtils.isNotNullandEmpty(eventToDate)) {

			EventServiceValidator.validateEventDates(eventFromDate, eventToDate);
		}

		shopEventResponse = shopEventDataIntf.getShopEventHistoryV3(shopVisitEventRequest);
		if (!DataServiceUtils.isCollectionNotEmpty(shopEventResponse.getEventsDetails())) {
			LOGGER.info("Exception occured in getShopEventHistoryV3-"
					+ EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_EMPTY_LIST);
			throw new DataServiceException(
					DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.NO_DATA_FOUND_E1003,
					DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_EMPTY_LIST,
					EventsAppConstants.ERROR_UMSG_ENGINE_EVENT_HISTORY_EMPTY_LIST);
		}else{
			shopEventResponse.setStatus(DataServiceConstants.SUCCESS);
		}
		LOGGER.info("getShopEventHistoryV3 App Impl Layer: End");
		return shopEventResponse;
	}

	@Override
	public ShopEventDetailsResponse getShopEventHistoryDetailsV3(ShopVisitEventRequest shopVisitEventRequest) throws DataServiceException {

		LOGGER.info("getShopEventHistoryDetailsV3 App Impl Layer: Begin");
		String eventFromDate = null;
		String eventToDate = null;
		eventFromDate = shopVisitEventRequest.getEventFromDate();
		eventToDate = shopVisitEventRequest.getEventToDate();
		ShopEventDetailsResponse shopEventResponse = new ShopEventDetailsResponse();
		//either ESN or Event No or (Engine Model and Event Date range) is mandatory 
		if (DataServiceUtils.isNullandEmpty(shopVisitEventRequest.getEsn())
				&& DataServiceUtils.isNullandEmpty(shopVisitEventRequest.getEventNo())
				&& (DataServiceUtils.isNullandEmpty(shopVisitEventRequest.getEngineModel())
						|| DataServiceUtils.isNullandEmpty(eventFromDate)
						|| DataServiceUtils.isNullandEmpty(eventToDate))) {

			LOGGER.info("Exception occured in getShopEventHistoryDetailsV3-"
					+ EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_DETAILS_MISSING_INPUT);
			throw new DataServiceException(
					DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.MISSING_INPUT_ERROR_E1001,
					DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_DETAILS_MISSING_INPUT,
					EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_DETAILS_MISSING_INPUT);
		}
		if (DataServiceUtils.isNotNullandEmpty(shopVisitEventRequest.getEsn())) {
			EventServiceValidator.validate(
					EventServiceValidator.DATA_ELEMENT_ESN, shopVisitEventRequest.getEsn());

		}
		
		if (DataServiceUtils.isNotNullandEmpty(eventFromDate) && DataServiceUtils.isNotNullandEmpty(eventToDate)) {

			EventServiceValidator.validateEventDates(eventFromDate, eventToDate);
		}

		shopEventResponse = shopEventDataIntf.getShopEventHistoryDetailsV3(shopVisitEventRequest);
		if (!DataServiceUtils.isCollectionNotEmpty(shopEventResponse.getEngineSeriesDetails())) {
			LOGGER.info("Exception occured in getShopEventHistoryDetailsV3-"
					+ EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_EMPTY_LIST);
			throw new DataServiceException(
					DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.NO_DATA_FOUND_E1003,
					DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.ERROR_MSG_ENGINE_EVENT_HISTORY_EMPTY_LIST,
					EventsAppConstants.ERROR_UMSG_ENGINE_EVENT_HISTORY_EMPTY_LIST);
		}else{
			shopEventResponse.setStatus(DataServiceConstants.SUCCESS);
		}

		LOGGER.info("getShopEventHistoryDetailsV3 App Impl Layer: End");
		return shopEventResponse;
	}
}