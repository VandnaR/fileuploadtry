package com.wipro.fileupload.feignproxy;

/**
 * Created by DE393632 on 1/17/2019.
 */
public class ClientPayload {

    private String clientAppId;
    private String clientAppSecret;
    private String authorizationToken;
    private String resourceRequestURL;
    private String methodType;
    private String username;

    public ClientPayload() {
    }

    public ClientPayload(String clientAppId, String clientAppSecret, String authorizationToken, String resourceRequestURL, String methodType, String username) {
        this.clientAppId = clientAppId;
        this.clientAppSecret = clientAppSecret;
        this.authorizationToken = authorizationToken;
        this.resourceRequestURL = resourceRequestURL;
        this.methodType = methodType;
        this.username = username;
    }

    public String getClientAppId() {
        return clientAppId;
    }

    public void setClientAppId(String clientAppId) {
        this.clientAppId = clientAppId;
    }

    public String getClientAppSecret() {
        return clientAppSecret;
    }

    public void setClientAppSecret(String clientAppSecret) {
        this.clientAppSecret = clientAppSecret;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getResourceRequestURL() {
        return resourceRequestURL;
    }

    public void setResourceRequestURL(String resourceRequestURL) {
        this.resourceRequestURL = resourceRequestURL;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ClientPayload{" +
                "clientAppId='" + clientAppId + '\'' +
                ", clientAppSecret='" + clientAppSecret + '\'' +
                ", authorizationToken='" + authorizationToken + '\'' +
                ", resourceRequestURL='" + resourceRequestURL + '\'' +
                ", methodType='" + methodType + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
