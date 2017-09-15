package com.geaviation.eds.service.events.app.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.geaviation.eds.service.common.exception.DataServiceException;

public class EventsURLBuilder
{
  private StringBuilder urlBuffer = new StringBuilder();
  private Map<String, String> queryParams = new HashMap<String,String>();
  

  public EventsURLBuilder() {
	//no argument constructor
  }

  public void addPath(String path)
  {
    if ((path == null) || (path.isEmpty())) {
      return;
    }

    int queryIndex = path.indexOf(63);
    String preQueryPath;
    if (queryIndex >= 0) {
      preQueryPath = path.substring(0, queryIndex);

      if (path.length() > queryIndex) {
        String qString = path.substring(queryIndex + 1);
        StringTokenizer st = new StringTokenizer(qString, "&");
        while (st.hasMoreTokens()) {
          String paramPair = st.nextToken();
          int equalsIndex = paramPair.indexOf(61);
          String value;
          String key;
          if (equalsIndex > 0) {
            key = paramPair.substring(0, equalsIndex);            
            if (equalsIndex < paramPair.length()){
              value = paramPair.substring(equalsIndex + 1);
            }else{
              value = "";
            }
          }
          else {
            key = paramPair;
            value = null;
          }
          this.queryParams.put(key, value);
        }
      }
    } else {
      preQueryPath = path;
    }

    if (this.urlBuffer.length() > 0) {
      if (this.urlBuffer.charAt(this.urlBuffer.length() - 1) == '/') {
        while (preQueryPath.startsWith("/")) {
          preQueryPath = preQueryPath.substring(1);
        }
      }
      if ((!preQueryPath.isEmpty()) && (!preQueryPath.startsWith("/"))) {
        preQueryPath = new StringBuilder().append("/").append(preQueryPath).toString();
      }

    }

    this.urlBuffer.append(preQueryPath);
  }

  public EventsURLBuilder addParam(String name, String value) throws DataServiceException
	{
		try {
			this.queryParams.put(URLEncoder.encode(name, "UTF-8"), URLEncoder.encode(value, "UTF-8"));
			return this;
		} catch (UnsupportedEncodingException e) {
			throw new DataServiceException("Failed to add query params.", e);
		}
	}

  public String build()
  {
    boolean isFirstQP = true;

    StringBuilder qpBuf = new StringBuilder();

    for (Map.Entry<String,String> e : this.queryParams.entrySet()) {
      if (isFirstQP) {
        qpBuf.append("?");
        isFirstQP = false;
      } else {
        qpBuf.append("&");
      }

      qpBuf.append((String)e.getKey());
      if (e.getValue() != null) {
        qpBuf.append("=");
        qpBuf.append((String)e.getValue());
      }
    }

    return new StringBuilder().append(this.urlBuffer.toString()).append(qpBuf.toString()).toString();
  }

  @Override
  public String toString()
  {
    return build();
  }

  public void removeParam(String qpAuthToken) {
    this.queryParams.remove(qpAuthToken);
  }
}
