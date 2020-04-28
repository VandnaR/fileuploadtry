package com.wipro.fileupload.migrationAnalysis.dto;

public class ContextDTO {

    private Long uploadLocationContextId;
    private String subFolder;
    private String fileName;

    private String source;
    private String target;

    public ContextDTO(){

    }

    public Long getUploadLocationContextId() {
        return uploadLocationContextId;
    }

    public void setUploadLocationContextId(Long uploadLocationContextId) {
        this.uploadLocationContextId = uploadLocationContextId;
    }

    public String getSubFolder() {
        return subFolder;
    }

    public void setSubFolder(String subFolder) {
        this.subFolder = subFolder;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "ContextDTO{" +
                "uploadLocationContextId=" + uploadLocationContextId +
                ", subFolder='" + subFolder + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
