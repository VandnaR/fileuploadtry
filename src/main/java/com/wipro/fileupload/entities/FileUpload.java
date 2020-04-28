package com.wipro.fileupload.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the file_upload database table.
 * 
 */
@Entity
@Table(name="file_upload")
@NamedQuery(name="FileUpload.findAll", query="SELECT f FROM FileUpload f")
public class FileUpload implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="file_upload_id")
	private Long fileUploadId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	@Column(name="file_name")
	private String fileName;

	@Column(name="file_upload_url")
	private String fileUploadUrl;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_on")
	private Date updatedOn;

	@Column(name="context_client_id")
	private Long contextClientId;

	@Column(name="entity_context_id")
	private Long entityContextId;

	@Column(name="upload_location_context_id")
	private Long uploadLocationContextId;

	public FileUpload() {
	}

    public Long getFileUploadId() {
        return fileUploadId;
    }

    public void setFileUploadId(Long fileUploadId) {
        this.fileUploadId = fileUploadId;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUploadUrl() {
        return fileUploadUrl;
    }

    public void setFileUploadUrl(String fileUploadUrl) {
        this.fileUploadUrl = fileUploadUrl;
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
}