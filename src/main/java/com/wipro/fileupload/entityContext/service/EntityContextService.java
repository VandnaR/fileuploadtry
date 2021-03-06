package com.wipro.fileupload.entityContext.service;

import com.wipro.fileupload.entityContext.dao.EntityContextRepository;
import com.wipro.fileupload.entities.EntityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EntityContextService implements IEntityContextService{

    @Autowired
    EntityContextRepository entityContextRepository;

    @Override
    public EntityContext getEntityContextById(Long id) throws EntityNotFoundException{
        EntityContext entityContext = entityContextRepository.findById(id).get();
        if(entityContext == null) throw new EntityNotFoundException("EntityContext record not found for the given id " + id);
        return entityContext;
    }

    @Override
    public EntityContext updateEntityContext(Long id, EntityContext entityContext) throws EntityNotFoundException{
        EntityContext entityContextEntity = entityContextRepository.findById(id).get();
        if(entityContextEntity == null) throw new EntityNotFoundException("EntityContext record not found for the given id " + entityContext.getEntityContextId());
        entityContextEntity.setEntityContextName(entityContext.getEntityContextName());
        entityContextEntity.setContextClientId(entityContext.getContextClientId());
        entityContextEntity.setUpdatedBy(entityContext.getUpdatedBy());
        entityContextEntity.setUpdatedOn(new java.util.Date());
        entityContextEntity = entityContextRepository.save(entityContextEntity);
        return entityContextEntity;
    }

    @Override
    public EntityContext addEntityContext(EntityContext entityContext) throws IllegalAccessException{
        if(entityContext.getEntityContextId() != null)
            throw new IllegalAccessException("You cannot add a entityContext with the id, this field is autogenerated!");
        entityContext.setCreatedOn(new java.util.Date());
        return entityContextRepository.save(entityContext);
    }

    @Override
    public void deleteEntityContextById(long entityContextId) throws EntityNotFoundException{
        entityContextRepository.deleteById(entityContextId);
    }

}
