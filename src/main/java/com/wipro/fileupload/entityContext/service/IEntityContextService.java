package com.wipro.fileupload.entityContext.service;

import com.wipro.fileupload.entities.EntityContext;
import javax.persistence.EntityNotFoundException;

public interface IEntityContextService{

    EntityContext getEntityContextById(Long id) throws EntityNotFoundException;
    EntityContext updateEntityContext(Long id, EntityContext entityEntityContext) throws EntityNotFoundException;
    EntityContext addEntityContext(EntityContext entityEntityContext) throws IllegalAccessException;
    void deleteEntityContextById(long entityEntityContextId);
}
