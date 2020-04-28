package com.wipro.fileupload.feignproxy;

import java.util.Date;

/**
 * Created by DE393632 on 1/17/2019.
 */
public class Token {
    private Long tokenId;

    private Long clientId;

    private String createdBy;


    private Date createdOn;

    private double expiresIn;

    private String refreshToken;

    private String token;

    private String scope;

    private String status;

    public Token(){

    }

    public Token(Long tokenId, Long clientId, String createdBy, Date createdOn, double expiresIn, String refreshToken, String token, String scope, String status) {
        this.tokenId = tokenId;
        this.clientId = clientId;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.token = token;
        this.scope = scope;
        this.status = status;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public double getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(double expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenId=" + tokenId +
                ", clientId=" + clientId +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", token='" + token + '\'' +
                ", scope='" + scope + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
