package com.geaviation.eds.service.events.data.api;

import java.util.List;

import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.events.model.maintenance.EngineUtilizationHistoryResponse;
import com.geaviation.eds.service.events.model.maintenance.MaintenanceData;
import com.geaviation.eds.service.events.model.maintenance.MaintenanceReq;
import com.geaviation.eds.service.events.model.maintenance.RDOMaintenanceReq;
import com.geaviation.eds.service.events.model.maintenance.WaterWashMaintenanceData;

public interface IEventsMaintenanceData {

	List<MaintenanceData> getMaintenanceData(String eventid) throws DataServiceException;

	List<WaterWashMaintenanceData> getRDOMaintenanceData(RDOMaintenanceReq rdoMaintenanceReq) throws DataServiceException;

	List<WaterWashMaintenanceData> getFDMMaintenanceData(MaintenanceReq maintenanceReq)
			throws DataServiceException;

	List<EngineUtilizationHistoryResponse> getEngineUtilizationHistory(MaintenanceReq maintenanceReq) throws DataServiceException;

	List<WaterWashMaintenanceData> getShopvisitOverhaulData(MaintenanceReq maintenanceReq)
			throws DataServiceException;
	
}
