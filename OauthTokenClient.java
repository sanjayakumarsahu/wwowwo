package com.geaviation.eds.service.common.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.common.exception.DataServiceTechnicalException;

@Component
public class OauthTokenClient{
	
	private static final Log LOGGER = LogFactory.getLog(OauthTokenClient.class);
	
	@SuppressWarnings("deprecation")
	private DefaultHttpClient client;
	
	
	private void initializeClient() throws DataServiceException{
		client = new DefaultHttpClient();
		initSSLPolicy();
	}
		
	/**
	 * This method will return the HttpResponse which contains the token information from Ping Federate AS.
	 * @param url
	 * @param token
	 * @param payload
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	public HttpResponse doPost(String url, final String payload, String contentType) throws DataServiceException {
		LOGGER.info("OauthTokenClient : doPost : Begin");
		
		initializeClient();
		HttpUriRequest request = new HttpPost(url);
		HttpResponse resp=null;
		HttpEntityEnclosingRequest entityEncReq = (HttpEntityEnclosingRequest) request;
		EntityTemplate ent = new EntityTemplate(new ContentProducer() {
			public void writeTo(OutputStream outputStream) throws IOException {
				outputStream.write(payload.getBytes());
				outputStream.flush();
			}
		});
		ent.setContentType(contentType);
		entityEncReq.setEntity(ent);
		
		try {
			
			resp = client.execute(request);
			
		}catch (Exception e) {
			LOGGER.error("OauthTokenClient : doPost Exception ", e);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
					DataServiceConstants.ERROR_STATUS,DataServiceConstants.OAUTH_PING_ERROR,
					DataServiceConstants.OAUTH_PING_ERROR);
			
		}
				
		LOGGER.info("OauthTokenClient : doPost : End");
		return resp;
			
	}
	
	
	
	public String getResponsePayload(HttpResponse response) throws IOException {
		LOGGER.info("OauthTokenClient : getResponsePayload : Begin");
		StringBuilder buffer = new StringBuilder();
		InputStream in = null;
		try {
			if (response.getEntity() != null) {
			    in = response.getEntity().getContent();
			    int length;
			    byte[] tmp = new byte[2048];
			    while ((length = in.read(tmp)) != -1) {
			        buffer.append(new String(tmp, 0, length));
			    }
			}
		} catch (IllegalStateException e) {			
			LOGGER.error("Exception Occured: getResponsePayload:",e);
		} finally {
			if (in != null) {
				in.close();
			}
		}
		LOGGER.info("OauthTokenClient : getResponsePayload : End");
		
        return buffer.toString();
	}
	
	private void initSSLPolicy() throws DataServiceException {
		LOGGER.info("OauthTokenClient : initSSLPolicy : Begin");
		SSLSocketFactory sf = null;
		
		try {
			sf = getEasySSLSocketFactory();
		} catch (Exception e) {
			LOGGER.error("Exception Occured: initSSLPolicy:",e);
			throw new DataServiceTechnicalException("Unable to initialize SSL OauthTokenClient",e);
		} 
	
		Scheme https = new Scheme("https", 443, sf);
		SchemeRegistry schemeRegistry = client.getConnectionManager().getSchemeRegistry();
		schemeRegistry.register(https);
		LOGGER.info("OauthTokenClient : initSSLPolicy : End");
	}
	
	private SSLSocketFactory getEasySSLSocketFactory() 
	throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, DataServiceException 
	{
		LOGGER.info("OauthTokenClient : getEasySSLSocketFactory : Begin");
		try{
		TrustStrategy trustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] x509Certificates,
			String s) throws CertificateException {
				LOGGER.info("self trusting...");
				return true; // Accept Self-Signed Certs			
			}
		};
		
		SSLSocketFactory sslSocketFactory = null;
		//Bypass check for hostname verification
		sslSocketFactory = 
		new SSLSocketFactory(trustStrategy, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		LOGGER.info("OauthTokenClient : getEasySSLSocketFactory : End");
		return sslSocketFactory;
		
		}catch (Exception e){
			LOGGER.error("Exception Occured: getEasySSLSocketFactory:",e);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,
					DataServiceConstants.ERROR_STATUS,e.getMessage(),
					DataServiceConstants.OAUTH_PING_ERROR);
		}
		
	}	
	
		
}

