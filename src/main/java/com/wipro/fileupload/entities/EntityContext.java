package com.wipro.fileupload.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the entity_context database table.
 * 
 */
@Entity
@Table(name="entity_context")
@NamedQuery(name="EntityContext.findAll", query="SELECT e FROM EntityContext e")
public class EntityContext implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="entity_context_id")
	private Long entityContextId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	@Column(name="entity_context_name")
	private String entityContextName;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_on")
	private Date updatedOn;

	@Column(name="context_client_id")
	private Long contextClientId;

	public EntityContext() {
	}

	public Long getEntityContextId() {
		return entityContextId;
	}

	public void setEntityContextId(Long entityContextId) {
		this.entityContextId = entityContextId;
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

	public String getEntityContextName() {
		return entityContextName;
	}

	public void setEntityContextName(String entityContextName) {
		this.entityContextName = entityContextName;
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