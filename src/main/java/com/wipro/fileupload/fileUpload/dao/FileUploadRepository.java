package com.wipro.fileupload.fileUpload.dao;

import com.wipro.fileupload.entities.FileUpload;
import org.springframework.data.repository.CrudRepository;

public interface FileUploadRepository extends CrudRepository<FileUpload, Long> {

    //custom functions

}
