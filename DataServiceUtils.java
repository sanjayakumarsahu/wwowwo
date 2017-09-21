package com.geaviation.eds.service.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.properties.EncryptableProperties;
import org.springframework.core.io.ClassPathResource;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.common.exception.DataServiceTechnicalException;


/**
 * Data service utils
 *
 */
public class DataServiceUtils {

	private static final Log LOGGER = LogFactory.getLog(DataServiceUtils.class);

	private static final String PINGHEADER ="<h5><span style='font-family:verdana;color:003300'>";
	
	private static final String DATEFORMAT = "yyyy-MM-dd";

	private DataServiceUtils(){

	}


	public static Integer checkForNullInteger(Object obj) {
		Integer retVal = Integer.parseInt(DataServiceConstants.UNDEFINED_NUMBER_VALUE);
		if (obj != null) {
			retVal = (Integer) obj;
		}
		return retVal;
	}

	public static Long checkForNullLong(Object obj) {
		Long retVal = Long.parseLong(DataServiceConstants.UNDEFINED_NUMBER_VALUE);
		if (obj != null) {
			retVal = (Long) obj;
		}
		return retVal;
	}

	public static Long checkForNullLongV2(Object obj) {
		Long retVal = Long.parseLong(DataServiceConstants.UNDEFINED_NUMBER_VALUE);

		retVal =new Long(null!=obj?obj.toString():"0"); 

		return retVal;
	}

	public static Double checkForNullDouble(Object obj) {
		Double retVal = Double.parseDouble(DataServiceConstants.UNDEFINED_NUMBER_VALUE);
		if (obj != null) {
			retVal = (Double) obj;
		}
		return retVal;
	}    

	public static Float checkForNullFloat(Object obj) {
		Float retVal = Float.parseFloat(DataServiceConstants.UNDEFINED_NUMBER_VALUE);
		if (obj != null) {
			retVal = (Float) obj;
		}
		return retVal;
	}

	public static Long checkForNullBigDecimal(Object obj) {
		if (obj == null) {
			return (new BigDecimal(
					DataServiceConstants.UNDEFINED_NUMBER_VALUE)).longValue();
		} else {
			return ((BigDecimal) obj).longValue();
		}
	}

	public static Float checkForNullBigDecimaltoFloat(Object obj) {
		if (obj == null) {
			return (new BigDecimal(
					DataServiceConstants.UNDEFINED_NUMBER_VALUE)).floatValue();
		} else {
			return ((BigDecimal) obj).floatValue();
		}
	}

	public static String checkForNullStringNumber(Object obj) {
		String retVal = DataServiceConstants.UNDEFINED_NUMBER_VALUE;
		if (obj != null) {
			retVal = obj.toString();
		}
		return retVal;
	}

	/**
	 * Returns true if the given string is not null and not empty
	 * @param strData
	 * @return boolean
	 */
	public static boolean isNotNullandEmpty(final String strData) {
		boolean isValid = false;
		if (strData != null && !("").equals(strData.trim())) {
			isValid = true;
		}
		return isValid;
	}


	public static boolean isNullandEmpty(final String strData) {
		boolean isValid = false;
		if (null==strData || strData.trim().equals(DataServiceConstants.EMPTY_STRING)) {
			isValid = true;
		}
		return isValid;
	}

	public static String getAsString(Object obj) {
		if (null != obj) {
			return obj.toString().trim();
		}
		return DataServiceConstants.EMPTY_STRING;
	}

	public static String formatDate(String inputDate) throws DataServiceException{
		String formattedDate = null;
		Date tempDate=null;
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");

		if(isNotNullandEmpty(inputDate)){
			try
			{				
				synchronized (dateFormat1) {
					tempDate=dateFormat1.parse(inputDate);	
				}
				synchronized (dateFormat2) {
					formattedDate = dateFormat2.format(tempDate);		
				}		     

			} catch (ParseException ex){
				throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.DATE_FORMAT_ERROR_E1008, DataServiceConstants.ERROR_STATUS, DataServiceConstants.DATE_FORMAT_ERROR_MSG, DataServiceConstants.DATE_FORMAT_ERROR_MSG);
			}
		}
		return formattedDate;
	}

	public static String dateFormatter(String inputDate) throws DataServiceException{
		String formattedDate = null;
		Date tempDate=null;
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat(DATEFORMAT);

		if(DataServiceUtils.isNotNullandEmpty(inputDate)){
			try
			{				
				synchronized (dateFormat1) {
					tempDate=dateFormat1.parse(inputDate);	
				}
				synchronized (dateFormat2) {
					formattedDate = dateFormat2.format(tempDate);		
				}		     

			} catch (ParseException ex){
				LOGGER.error("DataAccessException occured in dateFormatter =>" + ex);
				throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.DATE_FORMAT_ERROR_E1008, DataServiceConstants.ERROR_STATUS, DataServiceConstants.DATE_FORMAT_ERROR_MSG, DataServiceConstants.DATE_FORMAT_ERROR_MSG);
			}
		}
		return formattedDate;
	}
	public static String checkForNullStringValue(Object obj) {
		String retVal = null;
		if (obj != null) {
			retVal = obj.toString();
		}
		return retVal;
	}
	public static String checkForNullStringReturnsNull(Object obj) {
		String retVal = null;
		if (obj != null) {
			retVal = obj.toString();
		}
		return retVal;
	}

	public static String checkForNullStringReturnsUnDefined(Object obj) {
		String retVal = "NOT DEFINED";
		if (obj != null) {
			retVal = obj.toString();
		}
		return retVal;
	}

	public static String readSwaggerData(ClassPathResource swaggerFile){
		StringBuilder swaggerJson = null;
		String text = "";
		InputStreamReader isr = null;
		BufferedReader reader = null;
		InputStream is = null;
		ClassPathResource swaggerFiles = swaggerFile;

		try {
			swaggerJson = new StringBuilder();
			is = swaggerFiles.getInputStream();
			if (swaggerFiles.getInputStream() != null) {
				isr = new InputStreamReader(is);
				reader = new BufferedReader(isr);

				while ((text = reader.readLine()) != null) {

					swaggerJson.append(text);
					swaggerJson.append("\n");
				}
				reader.close();
				isr.close();
				is.close();
			}

		} catch (IOException e) {
			LOGGER.error("EXCEPTION OCCURED IN  SWAGGER JSON READ :"
					+ e.getLocalizedMessage());
			throw new DataServiceTechnicalException(e.getMessage(),	e);
		} catch (Exception e) {
			LOGGER.error("EXCEPTION OCCURED IN  SWAGGER JSON READ :"
					+ e.getLocalizedMessage());
			throw new DataServiceTechnicalException(e.getMessage(),	e);
		}finally {
			swaggerFiles = null;
			text = "";
			is = null;
			reader=null;
			isr=null;
			is=null;

		}
		return null!=swaggerJson?swaggerJson.toString():"";
	}

	public static void validateConsumerApp(String consumerApp) throws DataServiceException{

		if(!isNotNullandEmpty(consumerApp))
		{		

			LOGGER.info("inside validateConsumerApp");
			LOGGER.error(DataServiceConstants.MISSING_INPUT_ERROR_E1001+ ":"
					+ DataServiceConstants.MISSING_CONSUMERAPP_MSG);
			throw new DataServiceException(
					DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.MISSING_INPUT_ERROR_E1001,
					DataServiceConstants.ERROR_STATUS,DataServiceConstants.MISSING_CONSUMERAPP_MSG,
					DataServiceConstants.MISSING_CONSUMERAPP_MSG);
		}
	}

	public static String getSecurePassword(String key,String databasePassword,String encryptionAlgo,String deccryptionkey) throws IOException {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		EnvironmentStringPBEConfig config= new EnvironmentStringPBEConfig();
		config.setAlgorithm(encryptionAlgo);
		config.setPassword(deccryptionkey);

		encryptor.setConfig(config);

		Properties props = new EncryptableProperties(encryptor);
		props.setProperty(key, databasePassword);
		return props.getProperty(key); 
	}

	public static int instr(String data, String searchStr,
			int begIndex, int occurIndex) {
		int pos = 0;
		int occurNumber = 0;
		String tempStr;
		int beg;
		int i;
		int length;
		int ssLength;
		boolean flag = false;

		if (begIndex >= 0) {
			beg = begIndex;
			tempStr = data.substring(begIndex);

			for (i = 0; i < occurIndex; i++) {
				pos = tempStr.indexOf(searchStr);

				if (i == 1)
					beg = beg + pos;
				else
					beg = beg + pos;

				tempStr = data.substring(beg + 1);
			}
			if (pos == 0)
				return 0;
			else
				return beg;

		} else {
			ssLength = searchStr.length();
			length = data.length();
			beg = length + begIndex - ssLength + 1;

			while (beg > 0) {

				tempStr = data.substring(beg, beg + ssLength);

				pos = tempStr.indexOf(searchStr);

				if (pos >= 0) {
					occurNumber = occurNumber + 1;

					if (occurNumber == occurIndex) {

						flag = true;
						break;

					}

				}

				beg = beg - 1;
			}

			if (flag) {
				return beg;
			}
			return 0;
		}

	}
	public static String queryModulaterLimit(List<String> listValues) {
		StringBuilder clause = new StringBuilder();
		StringBuilder finalClause = new StringBuilder();
		LOGGER.info("DataServiceUtils:queryModulaterLimit called ");
		if (listValues != null && !listValues.isEmpty()) {

			int size = listValues.size();

			for (int idx = 0; idx < size; idx++) {
				clause = clause.append(", '").append(listValues.get(idx)).append("'");
				if (idx != 0 && idx % 999 == 0) {
					finalClause.append(" OR ").append(" IN (").append(clause.replace(0, 1, "")).append(") ");
					clause.delete(0, clause.length());
				}
			}
			if (clause.length() > 0)
			{
				finalClause.append(" OR ").append(" IN (").append(clause.replace(0, 1, "")).append(") ");
			}

		}

		if (finalClause.length() > 0)
		{
			finalClause.replace(0, 3, "");
		}
		return finalClause.toString();
	}
	
	public static String queryModulaterLimit(List<String> listValues,String fieldName) {
		StringBuilder clause = new StringBuilder();
		StringBuilder finalClause = new StringBuilder();
		LOGGER.info("DataServiceUtils:queryModulaterLimit called ");
		if (DataServiceUtils.isCollectionNotEmpty(listValues)) {

			int size = listValues.size();

			for (int idx = 0; idx < size; idx++) {
				clause = clause.append(", '").append(listValues.get(idx)).append("'");
				if (idx != 0 && idx % 2999 == 0) {
					if(finalClause.length() > 0){
						finalClause.append(" OR ");
					}
					finalClause.append(fieldName).append(" IN (").append(clause.replace(0, 1, "")).append(") ");
					clause.delete(0, clause.length());
				}
			}
			
			if (clause.length() > 0 )
			{
				if(finalClause.length() > 0)
				{
					finalClause.append(" OR ").append(fieldName).append(" IN (").append(clause.replace(0, 1, "")).append(") ");
				}
				else
				{
					finalClause.append(fieldName).append(" IN (").append(clause.replace(0, 1, "")).append(") ");
				}
			}

		}
		
		return finalClause.toString();
	}

	public static String dateFormat(String removalDate ){
		SimpleDateFormat sdfmt2= new SimpleDateFormat("yyyy-mm-dd");
		Date date; 	
		String dateRemoval = null;

		String[] dateFormats = {
				"mm/dd/yyyy",
				"dd-MMM-yyyy",
				"m/d/yyyy",
				"yyyy-mm-dd"
		};

		for (int i = 0; i < dateFormats.length; i++)
		{

			String format = dateFormats[i];
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);

			try
			{

				date = dateFormat.parse(removalDate);
				dateRemoval = sdfmt2.format(date);
				break;
			}
			catch (Exception e) {
				LOGGER.error("DateFormat Exception => " + e);
			}
		}
		return dateRemoval;
	}

	public static boolean isCollectionNotEmpty(final Map<String, String> map) {
		boolean isValid =false;
		if(map != null && !map.isEmpty()){
			isValid=true;
		}
		return isValid;
	}

	public static boolean isCollectionNotEmpty(	final Collection<? extends Object> list) {
		boolean isValid =false;
		if(list != null && !list.isEmpty()){
			isValid=true;
		}
		return isValid;
	}

	public static String convertDate(String srcDate) {
		SimpleDateFormat srcDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			Date dtSrc = srcDateFormat.parse(srcDate);
			SimpleDateFormat destDateFormat = new SimpleDateFormat(DATEFORMAT);
			return destDateFormat.format(dtSrc);
		} catch (ParseException e) {
			LOGGER.error("Error in parsing date", e);
		}
		return null;
	}

	public static Method[] getPingMethods(Method[] methods1,Method[] methods2){
		Method[] methods=methods1;
		Method[] methodsC=methods2;
		if(methods==null)
			methods= methodsC;
		else
			methods = (Method[]) ArrayUtils.addAll(methods,methodsC);

		return methods;
	}
	public static StringBuilder getPingResponse(StringBuilder pingResp ,Method[] methods2){

		int i = 0;
		int j = 0;
		StringBuilder pingResponse=pingResp;
		Method[] methods=methods2;
		if(methods!=null){
			while (i < methods.length) {
				if(!"ping".equals(methods[i].getName()) && (methods[i].getReturnType().getName().contains("Response") || methods[i].getReturnType().getName().contains("Completion"))||methods[i].getReturnType().getName().contains("PartDetails")  || methods[i].getName().contains("getPlot")){
					j ++; 
					pingResponse.append(PINGHEADER+j+") "+ methods[i].getName() + "</span></h5>");
				}
				i++;
			}
		}

		return pingResponse;
	}
	
	public static Date validateInputEventDate(String strDate)throws DataServiceException{

		Date eventEndDate = null;
		if(DataServiceUtils.isNotNullandEmpty(strDate)){
			eventEndDate = parseDateFormat(strDate);
		}
		return eventEndDate;
	}

	public static Date parseDateFormat(String fromDate) throws DataServiceException{

		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);

		try{
			sdf.setLenient(false);
			return sdf.parse(fromDate);

		}
		catch(ParseException exp)
		{
			LOGGER.error("Exception occured in parseDateFormat-"
					+ DataServiceConstants.DATE_FORMAT_ERROR_MSG);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.DATE_FORMAT_ERROR_E1008,
					DataServiceConstants.ERROR_STATUS,
					DataServiceConstants.DATE_FORMAT_ERROR_MSG,
					DataServiceConstants.WRONG_DATE_FORMAT);
		}    

	}
	public static String getQueryByList(List<String> listValues) {
		StringBuilder clause = new StringBuilder();
		if (DataServiceUtils.isCollectionNotEmpty(listValues)) {
		clause = clause.append("IN ( ");
		for (int i = 0; i < listValues.size(); i++) {
		clause.append("?");
	    if (i != listValues.size() - 1) {
		clause.append(", ");
		}
		}
		clause.append(" )");
		}
		return clause.toString();
		}
	
	
	public static String generateQsForIn(int numQs) {
		String items = "(";
		for (int i = 0; i < numQs; i++) {
			if (i != 0) {
				items += ", ";
			}
			items += "?";
		}
		return items+")";
	}
}
