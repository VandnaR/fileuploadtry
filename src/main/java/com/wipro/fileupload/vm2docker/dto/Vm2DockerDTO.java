package com.wipro.fileupload.vm2docker.dto;

public class Vm2DockerDTO {
    private String s3Path;
    private String appName;
    private String repoName;

    public Vm2DockerDTO(){}

    public String getS3Path() {
        return s3Path;
    }

    public void setS3Path(String s3Path) {
        this.s3Path = s3Path;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    @Override
    public String toString() {
        return "Vm2DockerDTO{" +
                "s3Path='" + s3Path + '\'' +
                ", appName='" + appName + '\'' +
                ", repoName='" + repoName + '\'' +
                '}';
    }
}
