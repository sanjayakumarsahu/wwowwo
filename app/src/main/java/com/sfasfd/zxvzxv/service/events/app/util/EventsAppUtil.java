package com.geaviation.eds.service.events.app.util;


import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.geaviation.eds.service.events.model.maintenance.MaintenanceData;
import com.geaviation.eds.service.events.model.shopinfo.EventSortField;


public class EventsAppUtil {

	private static final Log LOGGER = LogFactory.getLog(EventsAppUtil.class);
	private EventsAppUtil() {

	}

	/**
	 * Returns true if the given string is not null and not empty
	 * @param strData
	 * @return boolean
	 */
	public static boolean isNotNullandEmpty(final String strData) {
		boolean isValid = false;
		if (strData != null && !"".equals(strData.trim())) {
			isValid = true;
		}
		return isValid;
	}

	@SuppressWarnings("unchecked")
	public static <T> void sortList(List<MaintenanceData> lstMaintenanceData, List<EventSortField> lstSortFields) {

		if (lstMaintenanceData.isEmpty()) {
			return;
		}

		Class<?> clazz = lstMaintenanceData.get(0).getClass();
		ComparatorChain comparatorChain = new ComparatorChain();

		for (EventSortField sortField : lstSortFields) {

			comparatorChain.addComparator(new EventDynamicComparator<T>(clazz,
					sortField.getFieldName(),
					"asc".equals(sortField.getSortOrder())?true:false));
		}
		if (comparatorChain.size() > 0) {
			Collections.sort(lstMaintenanceData, comparatorChain);	
		}
	}

	/**
	 * This method will check whether the passed collection is not
	 * null & not empty
	 * @param list
	 * @return boolean
	 */
	public static boolean isCollectionNotEmpty(
			final Collection<Object> list) {
		return list != null && !list.isEmpty();
	}

	public static List<Object> filterResultList(
			List<Object> list, Integer start, Integer limit) {
		Integer lStart = start;
		if(lStart == null || lStart < 0){
			lStart = 0;
		}
		int toIndex = 1;
		if(limit != null && limit > 0 && limit <= list.size()) {
			if( (lStart + limit) <= list.size()){
				toIndex = (lStart) + limit;
			}
			if( (lStart + limit) > list.size()){
				toIndex = list.size();
			}						
		}
		return  list.subList(lStart, toIndex);
	}

	public static List<MaintenanceData> filterResultListV3(
			List<MaintenanceData> objectList, Integer begin, Integer givenLimit) {
		Integer lBegin = begin;
		if(lBegin == null || lBegin < 0){
			lBegin = 0;
		}
		int toIndex = 1;
		if(givenLimit != null && givenLimit > 0) {
			if(givenLimit <= objectList.size()){
				if( (lBegin + givenLimit) <= objectList.size()){
					toIndex = (lBegin) + givenLimit;
				}
				if( (lBegin + givenLimit) > objectList.size()){
					toIndex = objectList.size();
				}				
			}	else{
				toIndex = objectList.size();
			}		
		}
		return  objectList.subList(lBegin, toIndex);
	}

	public static String convertDateFormat(String dateInString)  {
		 
		 		String date = "";
		 	try{
		 			if (isNotNullandEmpty(dateInString)) {
		 				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 				Date inputDate = formatter.parse(dateInString);
		 
		 				SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		 
		 				date = mdyFormat.format(inputDate);
		 			}
		 	}catch (Exception exp) {
				LOGGER.error(exp);
			}
		 		return date;
		 	}
	
}
