package com.geaviation.eds.service.common.oauth;

import java.io.Serializable;



public class OauthToken implements Serializable{
	
	private String tokenType;
    private long expiresIn;
    private String refreshToken;
    private String accessToken;
    private String timestamp;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
    public String toString() {
        return "Token{" +
                "tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}
