

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;

@Api(value="/Events")
@RestController
@RequestMapping
public class EventsMaintenanceServicesImpl implements
IEventsMaintenanceServicesApi {
	@Autowired
	private IEventsMaintenanceApp mainEventAppIntf;



	@Path(value = "v1/maintenance/shopvisitoroverhaul")
	@ApiOperation(value = "Maintenance shopvisit or overhaul event details by ESN,Event ID,Event Startdate and Event Endate", 
			notes = "Maintenance event details by ESN,Event ID,Event Startdate and Event Endate", 
			response = WaterWashMaintEventResponse.class,
			extensions = {
				@Extension(
						name="serviceMetaData",				        
						properties = {
								@ExtensionProperty(name = "serviceNowCIName", value = "DDS-API-Generic"),
								@ExtensionProperty(name = "serviceOwner", value = "DDS Team"),
								@ExtensionProperty(name = "dataSource", value = "GEAGP_GEADW_FDM"),
								@ExtensionProperty(name = "isExternal", value =  "false"),
								@ExtensionProperty(name = "isCore", value =  "false"),
								@ExtensionProperty(name = "isMock", value =  "false"),
								@ExtensionProperty(name = "isInfo", value =  "true"),
								@ExtensionProperty(name = "specification", value =  "Swagger 2.0 Specification"),
								@ExtensionProperty(name = "responseObject", value =  "MaintenanceEventResponse"),
		                        @ExtensionProperty(name = "risklevel", value =  "Medium"),
								@ExtensionProperty(name = "dataClassification", value =  "GE Internal"),
								@ExtensionProperty(name = "mockdataRefid", value =  "16")

						})
			})
	@POST
	@RequestMapping(value = "v1/maintenance/shopvisitoverhaul", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }, consumes = { MediaType.APPLICATION_JSON })
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@ResponseStatus(HttpStatus.OK)
	public	WaterWashMaintEventResponse getShopvisitOverhaulData(@ApiParam(name = "maintenanceReq", value = "{\"esn\":[\"550485\",\"994278\"],\"eventStartDate\":\"15-01-2016\",\"eventEndDate\":\"26-01-2017\"}",required=true, allowMultiple = false)@RequestBody MaintenanceReq maintenanceReq,
			@ApiParam(name = "ConsumerApp", value = "Consumer Application eg. ",required=false, allowMultiple = false) @HeaderParam("ConsumerApp") @RequestHeader(value="ConsumerApp",required=false) String consumerApp) throws DataServiceException{
		WaterWashMaintEventResponse maintEventResp = null;
		maintEventResp = mainEventAppIntf.getShopvisitOverhaulData(maintenanceReq);

		return maintEventResp;
	}

}
