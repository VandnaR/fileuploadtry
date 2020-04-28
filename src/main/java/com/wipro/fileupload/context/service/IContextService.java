package com.wipro.fileupload.context.service;

import com.wipro.fileupload.entities.Context;
import javax.persistence.EntityNotFoundException;

public interface IContextService {

    Context getContextById(Long id) throws EntityNotFoundException;
    Context updateContext(Long id, Context context) throws EntityNotFoundException;
    Context addContext(Context context) throws IllegalAccessException;
    void deleteContextById(long contextId);
    Context getContextByClientAppIdAndClientAppSecret(String clientAppId,String clientAppSecret);
}
