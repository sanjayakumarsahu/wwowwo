package com.geaviation.eds.service.events.app.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.common.util.DataServiceUtils;
import com.geaviation.eds.service.events.app.api.IEventsInfoApp;
import com.geaviation.eds.service.events.app.constants.EventsAppConstants;
import com.geaviation.eds.service.events.data.api.IEventsInfoData;
import com.geaviation.eds.service.events.model.eventinfo.EventDetails;
import com.geaviation.eds.service.events.model.eventinfo.EventResponse;

@Component
public class EventsInfoAppImpl implements IEventsInfoApp {

	private static final Log LOGGER = LogFactory.getLog(EventsInfoAppImpl.class);
	@Autowired
	private IEventsInfoData eventDataIntf;

	@Override
	public EventResponse getEventInfo(String esn, String action)
			throws DataServiceException {

		LOGGER.info("getEventInfo App Impl Layer: Begin");
		LOGGER.info("Input Params Esn: "+ esn + "action:" + action);
		
		EventServiceValidator.validateAction(action);
		EventResponse response = new EventResponse();
		List<EventDetails> eventDetailsList = eventDataIntf.getEventInfoData(esn,action);

		if(DataServiceUtils.isCollectionNotEmpty(eventDetailsList)) {
			response.setEventDetails(eventDetailsList);
		}
		else {
			LOGGER.error(EventsAppConstants.EVENT_EMPTY_RESULT);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.NO_DATA_FOUND_E1003,DataServiceConstants.ERROR_STATUS,EventsAppConstants.EVENT_EMPTY_RESULT,DataServiceConstants.ESN_NUMBER_INVALID);
		}

		response.setEventDetails(eventDetailsList);
		LOGGER.info("getEventInfo App Impl Layer: End");
		return response;
	}

}
