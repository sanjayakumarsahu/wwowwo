package com.geaviation.eds.service.events.ws.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.util.DataServiceUtils;


public class LoggingInterceptor extends HandlerInterceptorAdapter {

	private static final Log LOGGER = LogFactory.getLog(LoggingInterceptor.class);



	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		LOGGER.info(DataServiceConstants.CONSUMER_APP+request.getHeader(DataServiceConstants.CONSUMER_APP));
        
    	String reqUrl = request.getRequestURI();
        
        if(null!=reqUrl && ( reqUrl.contains("/fdm") || reqUrl.contains("/rdo") || reqUrl.contains("/shopvisitoverhaul") || reqUrl.contains("/engineutilizationhistory")))
        {
        	DataServiceUtils.validateConsumerApp(request.getHeader(DataServiceConstants.CONSUMER_APP));
         return true; 
        }else{
        	LOGGER.info(DataServiceConstants.CONSUMER_APP+":"+request.getHeader(DataServiceConstants.CONSUMER_APP));
        }
        
        return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//Do nothing  
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
					throws Exception {
		//Do nothing  
	}

}
