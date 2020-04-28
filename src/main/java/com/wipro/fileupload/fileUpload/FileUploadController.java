package com.wipro.fileupload.fileUpload;

import com.wipro.fileupload.entities.FileUpload;
import com.wipro.fileupload.fileUpload.service.IFileUploadService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Controller
public class FileUploadController {

    @Autowired
    IFileUploadService fileUploadService;

    @GetMapping(value = "/fileUpload/{id}", produces = {"application/json", "application/xml"} )
    public @ResponseBody ResponseEntity<FileUploadBean> getFileUploadById(@PathVariable("id")Long id,
                                                                    @RequestHeader("username") String username) {

        FileUploadBean fileUploadBean = new FileUploadBean();
        ResponseEntity<FileUploadBean> responseEntity;
        if(username == null)
        {
            fileUploadBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        try {
            FileUpload fileUpload = fileUploadService.getFileUploadById(id);
            fileUploadBean.setFileUpload(fileUpload);
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            fileUploadBean.setErrorMessage(ex.getMessage());
            ex.printStackTrace();
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.NOT_FOUND);
        } catch (HibernateException ex) {
            fileUploadBean.setErrorMessage(ex.getMessage());
            ex.printStackTrace();
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            fileUploadBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping(value = "/fileUpload", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<FileUploadBean> addFileUpload(@RequestBody FileUpload fileUpload,
                                                                @RequestHeader("username") String username){
        FileUploadBean fileUploadBean = new FileUploadBean();
        ResponseEntity<FileUploadBean> responseEntity;
        if(username == null)
        {
            fileUploadBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        try {
            fileUpload.setCreatedBy(username);
            FileUpload fileUploadReturned = fileUploadService.addFileUpload(fileUpload);
            fileUploadBean.setFileUpload(fileUploadReturned);
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.OK);
        } catch (IllegalAccessException ex) {
            fileUploadBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.NOT_ACCEPTABLE);
        } catch (HibernateException ex) {
            fileUploadBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            fileUploadBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping(value = "/fileUpload/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<FileUploadBean> updateFileUpload(@RequestBody FileUpload fileUpload,
                                                                   @PathVariable("id")Long id,
                                                                   @RequestHeader("username") String username){
        FileUploadBean fileUploadBean = new FileUploadBean();
        ResponseEntity<FileUploadBean> responseEntity;

        if(username == null || (fileUpload.getFileUploadId() != null  && !fileUpload.getFileUploadId().equals(id)))
        {
            fileUploadBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }

        try {

            fileUpload.setUpdatedBy(username);
            FileUpload fileUploadReturned = fileUploadService.updateFileUpload(id, fileUpload);
            fileUploadBean.setFileUpload(fileUploadReturned);
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.OK);

        } catch (EntityNotFoundException ex) {

            fileUploadBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.NOT_FOUND);

        } catch (HibernateException ex) {

            fileUploadBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {

            ex.printStackTrace();
            fileUploadBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;
    }

    @DeleteMapping(value = "/fileUpload/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<String> deleteUserRecords(
            @PathVariable("id")Long id,
            @RequestHeader("username") String username){
        ResponseEntity<String> responseEntity;

        if(username == null)
        {
            return new ResponseEntity<>("Unauthorized Acccee", HttpStatus.UNAUTHORIZED);
        }
        try {
            fileUploadService.deleteFileUploadById(id);
            responseEntity = new ResponseEntity<>("Deleted successfully", HttpStatus.OK);

        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();

            responseEntity = new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);

        } catch (HibernateException ex) {
            ex.printStackTrace();

            responseEntity = new ResponseEntity<>("Hibernate Exception", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {


            ex.printStackTrace();
            responseEntity = new ResponseEntity<>("Cannot Delete because id is referential", HttpStatus.BAD_REQUEST);

        }
        return responseEntity;
    }

}

@XmlRootElement
class FileUploadBean implements Serializable {
    private FileUpload fileUpload;
    private String errorMessage;

    public void setFileUpload(FileUpload fileUpload){
        this.fileUpload = fileUpload;
    }
    public FileUpload getFileUpload(){
        return this.fileUpload;
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }

    @Override
    public String toString() {
        return "FileUploadBean{" +
                "fileUpload=" + fileUpload +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}


