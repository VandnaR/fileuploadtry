package com.wipro.fileupload.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the context database table.
 * 
 */
@Entity
@NamedQuery(name="Context.findAll", query="SELECT c FROM Context c")
public class Context implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="context_client_id")
	private Long contextClientId;

	@Column(name="client_app_id")
	private String clientAppId;

	@Column(name="client_app_name")
	private String clientAppName;

	@Column(name="client_app_secret")
	private String clientAppSecret;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	private String environment;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_on")
	private Date updatedOn;

	public Context() {
	}

	public Long getContextClientId() {
		return contextClientId;
	}

	public void setContextClientId(Long contextClientId) {
		this.contextClientId = contextClientId;
	}

	public String getClientAppId() {
		return clientAppId;
	}

	public void setClientAppId(String clientAppId) {
		this.clientAppId = clientAppId;
	}

	public String getClientAppName() {
		return clientAppName;
	}

	public void setClientAppName(String clientAppName) {
		this.clientAppName = clientAppName;
	}

	public String getClientAppSecret() {
		return clientAppSecret;
	}

	public void setClientAppSecret(String clientAppSecret) {
		this.clientAppSecret = clientAppSecret;
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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
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
}