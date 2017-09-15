

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;


@Component
public class EventsMaintenanceDataImpl implements IEventsMaintenanceData {

	private static final Log LOGGER = LogFactory.getLog(EventsMaintenanceDataImpl.class);
	@Autowired
	@Qualifier("dataSourceRDO")
	DataSource dataSourceRDO;
	
	@Autowired
	@Qualifier("dataSourceFDM")
	DataSource dataSourceFDM;


	
	public List<WaterWashMaintenanceData> getShopvisitOverhaulData(MaintenanceReq maintenanceReq) throws DataServiceException {
		
		LOGGER.info("getShopvisitOverhaulData Data Layer: Begin");

		List<WaterWashMaintenanceData> listMaintenanceData = null;
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;

		try {
			Date formattedEventFromDate=DateFormatUtil.getSVFormattedDate(maintenanceReq.getEventStartDate());
			Date formattedEventToDate=DateFormatUtil.getSVFormattedDate(maintenanceReq.getEventEndDate());
			List<String> esnList = maintenanceReq.getEsn();
			
			StringBuilder queryBuilder = new StringBuilder(EventsDataConstants.GET_SVOROVERHAUL_DETAILS_V1);
			for (int i = 0; i < esnList.size(); i++) {
				queryBuilder.append(" ?");
				if (i != esnList.size() - 1) {
					queryBuilder.append(",");
				}
			}
			queryBuilder.append(EventsDataConstants.GET_SVOROVERHAUL_DETAILS_V1_1);
			String fullquery = queryBuilder.toString();
						
			conn = dataSourceGP.getConnection();
			LOGGER.info(DataServiceConstants.DB_CONNECTION); 
			StopWatch queryTime = new StopWatch(); 
			queryTime.start();
			pstmt = conn.prepareStatement(fullquery);			
			String[] strArr = esnList.toArray(new String[esnList.size()]);
			pstmt.setObject(1, new java.sql.Date(formattedEventFromDate.getTime()));
			pstmt.setObject(2, new java.sql.Date(formattedEventToDate.getTime()));

			for (int i = 0; i < strArr.length; i++) {
				int j = i + 3;
				pstmt.setObject(j, strArr[i]);
			}
			resultSet = pstmt.executeQuery();				
			queryTime.stop();
			LOGGER.info(DataServiceConstants.DB_EXECUTION_TIME+ queryTime.getTotalTimeMillis());
			if (resultSet != null) {
				listMaintenanceData = populateShopvisitOverhaulData(resultSet);
			}
			SQLUtils.closeQuietly(conn, resultSet, pstmt);
		} 
		 catch (SQLException e) {
			SQLUtils.closeQuietly(conn, resultSet, pstmt);
			LOGGER.error(DataServiceConstants.DATA_ACCESS_EXCEPTION + e);
			throw new DataServiceTechnicalException(DataServiceConstants.DB_ERROR, e);
		} catch (ParseException e) {
			SQLUtils.closeQuietly(conn, resultSet, pstmt);
			LOGGER.error(DataServiceConstants.DATA_ACCESS_EXCEPTION + e);
			throw new DataServiceException(DataServiceConstants.DATE_FORMAT_ERROR_E1008,DataServiceConstants.DATE_FORMAT_ERROR_MSG);
		} 
		catch (DataAccessException ex) {			
			SQLUtils.closeQuietly(conn, resultSet, pstmt);
			LOGGER.error(DataServiceConstants.DATA_ACCESS_EXCEPTION + ex);
			throw new DataServiceException(DataServiceConstants.DB_ERROR, DataServiceConstants.DATA_ACCESS_EXCEPTION);
		}
		catch (Exception ex) {
			SQLUtils.closeQuietly(conn, resultSet, pstmt);
			LOGGER.error("Data base Exception occured in getShopvisitOverhaulData  => "
					+ ex);
			throw new DataServiceException(DataServiceConstants.DB_ERROR, ex);
		}
		finally {
			SQLUtils.closeQuietly(conn, resultSet, pstmt);

		}
		
		
		LOGGER.info("getShopvisitOverhaulData  Data Layer: End");

		return listMaintenanceData;
	}  
	
	private List<WaterWashMaintenanceData> populateShopvisitOverhaulData(ResultSet row) throws DataServiceException {
		List<WaterWashMaintenanceData> lstMaintenanceData = new ArrayList<WaterWashMaintenanceData>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DataServiceConstants.DATE_FORMAT_YYYY_MM_DD);
		String sequenceid = null;
		String fprFlag = null;
		WaterWashMaintenanceData maintData=null;
		try {
			while (row.next()) {
				maintData = new WaterWashMaintenanceData();
				maintData.setEngineId(DataServiceUtils
						.checkForNullStringReturnsNull(row.getString(EventsDataConstants.WE_ESN)));
				sequenceid = row.getString(EventsDataConstants.WE_EVENT_NO);
				if (DataServiceUtils.isNotNullandEmpty(sequenceid)) {
					maintData.setMaintActionSeqId(Integer.parseInt(sequenceid));
				}
			  maintData.setMaintenanceSummary(DataServiceUtils
						.checkForNullStringReturnsNull(row.getString(EventsDataConstants.WE_NARRATIVE)));
			  
			  
			  fprFlag =DataServiceUtils.checkForNullStringReturnsNull(row.getString(EventsDataConstants.WE_FPR_FLAG));
				if(fprFlag!=null && ("yes").equalsIgnoreCase(fprFlag))
				{ 
					maintData.setMaintenanceEventType(EventsDataConstants.WE_MAINTENANCE_EVENT_OVERHAUL);
				}
				
				else
				{
					maintData.setMaintenanceEventType(EventsDataConstants.WE_MAINTENANCE_EVENT_SHOPVISIT);
				}
				maintData.setMaintenanceEventSource(EventsDataConstants.WE_MAINTENANCE_SH_OVERHAUL_SOURCE);
				if (DataServiceUtils.isNotNullandEmpty(row.getString(EventsDataConstants.WE_EVENT_DATE))) {
					Date maindate = dateFormat.parse(
							DataServiceUtils.checkForNullStringReturnsNull(row.getString(EventsDataConstants.WE_EVENT_DATE)));
					maintData.setMaintenanceDate(dateFormat.format(maindate));
				} 
				
				if (DataServiceUtils.isNotNullandEmpty(row.getString(EventsDataConstants.COL_LAST_UPDATED_DATE))) {
					Date lastdate = dateFormat.parse(DataServiceUtils
							.checkForNullStringReturnsNull(row.getObject(EventsDataConstants.COL_LAST_UPDATED_DATE)));
					maintData.setLastUpdateDate(dateFormat.format(lastdate));
				} 
			
				lstMaintenanceData.add(maintData);
			}
			
			SQLUtils.closeQuietly(null, row, null);
		} 
		 catch (NumberFormatException e) {
			SQLUtils.closeQuietly(null, row, null);
			LOGGER.error(DataServiceConstants.DATA_ACCESS_EXCEPTION + e);
			throw new DataServiceException(DataServiceConstants.DB_ERROR, DataServiceConstants.NUMBER_FORMAT_ERR_MSG);
		} 
		catch (SQLException e) {
			SQLUtils.closeQuietly(null, row, null);
			LOGGER.error(DataServiceConstants.DATA_ACCESS_EXCEPTION + e);
			throw new DataServiceTechnicalException(DataServiceConstants.DB_ERROR, e);
		} catch (ParseException e) {
			SQLUtils.closeQuietly(null, row, null);
			LOGGER.error(DataServiceConstants.DATA_ACCESS_EXCEPTION + e);
			throw new DataServiceException(DataServiceConstants.DATE_FORMAT_ERROR_E1008,DataServiceConstants.DATE_FORMAT_ERROR_MSG);
		} finally {
			SQLUtils.closeQuietly(null, row, null);

		}
		return lstMaintenanceData;
	}

	
}