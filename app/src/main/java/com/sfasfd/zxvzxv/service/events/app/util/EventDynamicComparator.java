package com.geaviation.eds.service.events.app.util;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EventDynamicComparator<T> implements Comparator<T>  {

	private static final Object DATE_TIME_FORMAT = "DateTime";
	private final int sortAscending;
	private final Method method;
	private final String compareType;
	private Method formatMethod;

	private static final Log LOGGER = LogFactory.getLog(EventDynamicComparator.class);

	public EventDynamicComparator(Class<?> sortClass, String sortColumn, boolean ascending) {
		this.sortAscending = ascending?1:-1;
		this.method = getMethod(sortClass,"get",sortColumn);
		this.compareType = this.method==null?"":this.method.getReturnType().getName();
		this.formatMethod = getMethod(sortClass, "format", sortColumn);
	}

	@Override
	public int compare(T o1, T o2) {
		if (this.method == null){
			return 0;
		}
		int rtnCd = 0;

		if (o1 == null && o2 == null) {
			rtnCd = 0;
		} else {

			try {

				if(formatMethod != null) {
					String format = (String)formatMethod.invoke(o1, (Object[])null);
					if(format.equals(DATE_TIME_FORMAT)) {
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String s1 = (String)method.invoke(o1, (Object[])null);
						String s2 = (String)method.invoke(o2, (Object[])null);

						Date d1 = null;
						Date d2 = null;

						if(s1!=null && !"".equalsIgnoreCase(s1.trim())){
							
							d1 = dateFormat.parse(s1);
							
						}
						if(s2!=null && !"".equalsIgnoreCase(s2.trim())){
							
							d2 = dateFormat.parse(s2);
						}

						rtnCd = compareObjectsForNull(d1, d2);
						if(rtnCd == 2) {
							rtnCd = d1.compareTo(d2);
						}
					}
				}
				else {
					rtnCd  = compareObjects(o1, o2);
				}
			} catch (Exception e) {
				LOGGER.error("Error in EventDynamicComparator compare is: " + e);
				return 0;
			}
		}

		return rtnCd * sortAscending;
	}

	private int compareObjectsForNull(Object obj1, Object obj2) {
		int rtnCd = 2;

		if (obj1 == null && obj2 == null) {
			rtnCd = 0;
		} else if (obj1 == null) {
			rtnCd = -1;
		} else if (obj2 == null) {
			rtnCd = 1;
		}

		return rtnCd;
	}

	private int compareObjects(T o1, T o2)  {
		int rtnCd = 0;

		try {
			if ("java.lang.String".equals(compareType)) {
				String s1 = (String)method.invoke(o1, (Object[])null);
				String s2 = (String)method.invoke(o2, (Object[])null);
				rtnCd = compareObjectsForNull(s1, s2);
				if(rtnCd == 2) {
					rtnCd = s1.compareTo(s2);
				}
			}
			else if ("int".equals(compareType)) {
				rtnCd = ((Integer)method.invoke(o1, (Object[])null)).compareTo((Integer)method.invoke(o2, (Object[])null));
			} 
			else if("java.util.Date".equals(compareType)){

				Date d1 = (Date)method.invoke(o1, (Object[])null);
				Date d2 = (Date)method.invoke(o2, (Object[])null);

				rtnCd = compareObjectsForNull(d1, d2);
				if(rtnCd == 2) {
					rtnCd = d1.compareTo(d2);
				}
			}
			else if("java.lang.Integer".equals(compareType)){
				Integer i1 = (Integer)method.invoke(o1, (Object[])null);
				Integer i2 = (Integer)method.invoke(o2, (Object[])null);

				rtnCd = compareObjectsForNull(i1, i2);
				if(rtnCd == 2) {
					rtnCd = i1.compareTo(i2);
				}
			}
			else if("java.lang.Double".equals(compareType)){
				Double d1 = (Double)method.invoke(o1, (Object[])null);
				Double d2 = (Double)method.invoke(o2, (Object[])null);

				rtnCd = compareObjectsForNull(d1, d2);
				if(rtnCd == 2) {
					rtnCd = d1.compareTo(d2);
				}
			}
			else if("java.lang.Boolean".equals(compareType)){
				Boolean b1 = (Boolean)method.invoke(o1, (Object[])null);
				Boolean b2 = (Boolean)method.invoke(o2, (Object[])null);

				rtnCd = compareObjectsForNull(b1, b2);
				if(rtnCd == 2) {
					rtnCd = b1.compareTo(b2);
				}
			}
			else if("java.lang.Long".equals(compareType)){
				Long l1 = (Long)method.invoke(o1, (Object[])null);
				Long l2 = (Long)method.invoke(o2, (Object[])null);

				rtnCd = compareObjectsForNull(l1, l2);
				if(rtnCd == 2) {
					rtnCd = l1.compareTo(l2);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error in EventDynamicComparator compareObjects is: " + e);
			return 0;
		}

		return rtnCd;
	}

	private Method getMethod(Class<?> clazz, String prefix, String fieldName) {
		String methodName = prefix + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
		try {
			return clazz.getMethod(methodName, (Class[])null);
		}
		catch (Exception e) {
			LOGGER.info(e);
		}
		return null;
	}

}
