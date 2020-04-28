package com.wipro.fileupload.uploadFile.payload;

public class ContextDTO {
    private String clientAppId;
    private String clientAppSecret;
    private String subFolderPath;
    private Long contextClientId;
    private Long entityContextId;
    private Long uploadLocationContextId;

    public ContextDTO() {
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

    public String getSubFolderPath() {
        return subFolderPath;
    }

    public void setSubFolderPath(String subFolderPath) {
        this.subFolderPath = subFolderPath;
    }

    public Long getContextClientId() {
        return contextClientId;
    }

    public void setContextClientId(Long contextClientId) {
        this.contextClientId = contextClientId;
    }

    public Long getEntityContextId() {
        return entityContextId;
    }

    public void setEntityContextId(Long entityContextId) {
        this.entityContextId = entityContextId;
    }

    public Long getUploadLocationContextId() {
        return uploadLocationContextId;
    }

    public void setUploadLocationContextId(Long uploadLocationContextId) {
        this.uploadLocationContextId = uploadLocationContextId;
    }

    @Override
    public String toString() {
        return "ContextDTO{" +
                "clientAppId='" + clientAppId + '\'' +
                ", clientAppSecret='" + clientAppSecret + '\'' +
                ", subFolderPath='" + subFolderPath + '\'' +
                ", contextClientId=" + contextClientId +
                ", entityContextId=" + entityContextId +
                ", uploadLocationContextId=" + uploadLocationContextId +
                '}';
    }
}
