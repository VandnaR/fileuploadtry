package com.wipro.fileupload.uploadLocationContext.service;

import com.wipro.fileupload.entities.UploadLocationContext;
import javax.persistence.EntityNotFoundException;

public interface IUploadLocationContextService {

    UploadLocationContext getUploadLocationContextById(Long id) throws EntityNotFoundException;
    UploadLocationContext updateUploadLocationContext(Long id, UploadLocationContext uploadLocationUploadLocationContext) throws EntityNotFoundException;
    UploadLocationContext addUploadLocationContext(UploadLocationContext uploadLocationUploadLocationContext) throws IllegalAccessException;
    void deleteUploadLocationContextById(long uploadLocationUploadLocationContextId);
}
