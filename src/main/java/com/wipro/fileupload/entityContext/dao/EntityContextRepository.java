package com.wipro.fileupload.entityContext.dao;

import com.wipro.fileupload.entities.EntityContext;
import org.springframework.data.repository.CrudRepository;

public interface EntityContextRepository extends CrudRepository<EntityContext, Long> {

    //custom functions

}
