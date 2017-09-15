package com.geaviation.eds.service.events.app.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.events.app.api.IEventsForecastRemovalApp;
import com.geaviation.eds.service.events.app.constants.EventsAppConstants;
import com.geaviation.eds.service.events.data.api.IEventsForecastRemovalData;
import com.geaviation.eds.service.events.model.forecastremoval.ForecastRemovalDetails;
import com.geaviation.eds.service.events.model.forecastremoval.ForecastRemovalResponse;

@Component
public class EventsForecastRemovalAppImpl implements IEventsForecastRemovalApp {

	private static final Log LOGGER = LogFactory.getLog(EventsForecastRemovalAppImpl.class);
	@Autowired
	private IEventsForecastRemovalData forecastRemovalDataIntf;

	@Override
	public ForecastRemovalResponse getForecastRemoval(int noOfDays) throws DataServiceException {
		ForecastRemovalResponse forecastRemovalResponse = new ForecastRemovalResponse();

		LOGGER.info("getForecastRemoval App Impl Layer: Begin");
		LOGGER.info("Input Params noOfDays: "+ noOfDays);

		if (noOfDays < 1 || noOfDays > 30) {
			LOGGER.info("Exception occured in getForecastRemoval-" + EventsAppConstants.NO_OF_DAYS_LIMIT_FAILED);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.DATA_VALIDATION_ERROR_E1002, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.NO_OF_DAYS_LIMIT_FAILED, EventsAppConstants.NO_OF_DAYS_LIMIT_FAILED);
		}

		List<ForecastRemovalDetails> forecastRemovalDetails = forecastRemovalDataIntf
				.getForecastRemovalForecast(noOfDays);

		if (null == forecastRemovalDetails || forecastRemovalDetails.isEmpty()) {
			LOGGER.debug("Exception occured in getForecastRemoval-" + EventsAppConstants.FORECAST_REMOVAL_EMPTY_LIST);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.NO_DATA_FOUND_E1003,
					DataServiceConstants.ERROR_STATUS, EventsAppConstants.FORECAST_REMOVAL_EMPTY_LIST,
					EventsAppConstants.FORECAST_REMOVAL_EMPTY_LIST);
		} else {
			forecastRemovalResponse.setRemovalEnginesList(forecastRemovalDetails);
		}

		LOGGER.info("getForecastRemoval App Impl Layer: End");

		return forecastRemovalResponse;
	}
}
