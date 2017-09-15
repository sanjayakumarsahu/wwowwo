
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestHeader;


public interface IEventsMaintenanceServicesApi {



	/**
	 * This method is used to get shopVisit or overhaul details for an Engines,returns List<EngineUtilizationHistoryResponse>
	 *
	 * @param esn
	 * @param eventFromDate
	 * *@param eventToDate
	 * @return consumerApp
	 * @throws DataServiceException
	 */
	@Path("v1/maintenance/shopvisitoverhaul")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public WaterWashMaintEventResponse getShopvisitOverhaulData(
		final MaintenanceReq maintenanceReq,@RequestHeader(value="ConsumerApp") String consumerApp) throws DataServiceException;
}
