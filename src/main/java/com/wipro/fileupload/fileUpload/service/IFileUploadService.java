package com.wipro.fileupload.fileUpload.service;

import com.wipro.fileupload.entities.FileUpload;
import javax.persistence.EntityNotFoundException;

public interface IFileUploadService {

    FileUpload getFileUploadById(Long id) throws EntityNotFoundException;
    FileUpload updateFileUpload(Long id, FileUpload fileUpload) throws EntityNotFoundException;
    FileUpload addFileUpload(FileUpload fileUpload) throws IllegalAccessException;
    void deleteFileUploadById(long fileUploadId);
}
