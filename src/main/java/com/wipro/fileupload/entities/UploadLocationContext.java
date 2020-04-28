package com.wipro.fileupload.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the upload_location_context database table.
 * 
 */
@Entity
@Table(name="upload_location_context")
@NamedQuery(name="UploadLocationContext.findAll", query="SELECT u FROM UploadLocationContext u")
public class UploadLocationContext implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="upload_location_context_id")
	private Long uploadLocationContextId;

	@Column(name="access_key")
	private String accessKey;

	@Column(name="access_secret_key")
	private String accessSecretKey;

	@Column(name="bucket_name")
	private String bucketName;

	@Column(name="client_region")
	private String clientRegion;

	@Column(name="destination_type")
	private String destinationType;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_on")
	private Date updatedOn;

	@Column(name="context_client_id")
	private Long contextClientId;

	public UploadLocationContext() {
	}

    public Long getUploadLocationContextId() {
        return uploadLocationContextId;
    }

    public void setUploadLocationContextId(Long uploadLocationContextId) {
        this.uploadLocationContextId = uploadLocationContextId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecretKey() {
        return accessSecretKey;
    }

    public void setAccessSecretKey(String accessSecretKey) {
        this.accessSecretKey = accessSecretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getClientRegion() {
        return clientRegion;
    }

    public void setClientRegion(String clientRegion) {
        this.clientRegion = clientRegion;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getContextClientId() {
        return contextClientId;
    }

    public void setContextClientId(Long contextClientId) {
        this.contextClientId = contextClientId;
    }
}