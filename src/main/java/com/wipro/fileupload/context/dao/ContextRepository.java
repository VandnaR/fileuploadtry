package com.wipro.fileupload.context.dao;

import com.wipro.fileupload.entities.Context;
import org.springframework.data.repository.CrudRepository;

public interface ContextRepository extends CrudRepository<Context, Long> {

    //custom functions
    Context findByClientAppIdAndClientAppSecret(String clientAppId,String clientAppSecret);
}
