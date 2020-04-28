package com.wipro.fileupload.uploadLocationContext;

import com.wipro.fileupload.entities.UploadLocationContext;
import com.wipro.fileupload.uploadLocationContext.service.IUploadLocationContextService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Base64;

@Controller
public class UploadLocationContextController {

    @Autowired
    IUploadLocationContextService uploadLocationContextService;

    @GetMapping(value = "/uploadLocationContext/{id}", produces = {"application/json", "application/xml"} )
    public @ResponseBody ResponseEntity<UploadLocationContextBean> getUploadLocationContextById(@PathVariable("id")Long id,
                                                                    @RequestHeader("username") String username) {

        UploadLocationContextBean uploadLocationContextBean = new UploadLocationContextBean();
        ResponseEntity<UploadLocationContextBean> responseEntity;
        if(username == null)
        {
            uploadLocationContextBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        try {
            UploadLocationContext uploadLocationContext = uploadLocationContextService.getUploadLocationContextById(id);
            uploadLocationContextBean.setUploadLocationContext(uploadLocationContext);
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            uploadLocationContextBean.setErrorMessage(ex.getMessage());
            ex.printStackTrace();
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.NOT_FOUND);
        } catch (HibernateException ex) {
            uploadLocationContextBean.setErrorMessage(ex.getMessage());
            ex.printStackTrace();
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            uploadLocationContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping(value = "/uploadLocationContext", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<UploadLocationContextBean> addUploadLocationContext(@RequestBody UploadLocationContext uploadLocationContext,
                                                                @RequestHeader("username") String username){
        UploadLocationContextBean uploadLocationContextBean = new UploadLocationContextBean();
        ResponseEntity<UploadLocationContextBean> responseEntity;
        if(username == null)
        {
            uploadLocationContextBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        try {
            uploadLocationContext.setCreatedBy(username);

            //encode the access key and access secret key of the user before saving the user details
            uploadLocationContext.setAccessKey(Base64.getEncoder().encodeToString(uploadLocationContext.getAccessKey().getBytes("utf-8")));
            uploadLocationContext.setAccessSecretKey(Base64.getEncoder().encodeToString(uploadLocationContext.getAccessSecretKey().getBytes("utf-8")));

            UploadLocationContext uploadLocationContextReturned = uploadLocationContextService.addUploadLocationContext(uploadLocationContext);
            uploadLocationContextBean.setUploadLocationContext(uploadLocationContextReturned);
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.OK);
        } catch (IllegalAccessException ex) {
            uploadLocationContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.NOT_ACCEPTABLE);
        } catch (HibernateException ex) {
            uploadLocationContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            uploadLocationContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping(value = "/uploadLocationContext/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<UploadLocationContextBean> updateUploadLocationContext(@RequestBody UploadLocationContext uploadLocationContext,
                                                                   @PathVariable("id")Long id,
                                                                   @RequestHeader("username") String username){
        UploadLocationContextBean uploadLocationContextBean = new UploadLocationContextBean();
        ResponseEntity<UploadLocationContextBean> responseEntity;

        if(username == null || (uploadLocationContext.getUploadLocationContextId() != null  && !uploadLocationContext.getUploadLocationContextId().equals(id)))
        {
            uploadLocationContextBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }

        try {

            uploadLocationContext.setUpdatedBy(username);
            UploadLocationContext uploadLocationContextReturned = uploadLocationContextService.updateUploadLocationContext(id, uploadLocationContext);
            uploadLocationContextBean.setUploadLocationContext(uploadLocationContextReturned);
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.OK);

        } catch (EntityNotFoundException ex) {

            uploadLocationContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.NOT_FOUND);

        } catch (HibernateException ex) {

            uploadLocationContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {

            ex.printStackTrace();
            uploadLocationContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(uploadLocationContextBean, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;
    }

    @DeleteMapping(value = "/uploadLocationContext/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<String> deleteUserRecords(
            @PathVariable("id")Long id,
            @RequestHeader("username") String username){
        ResponseEntity<String> responseEntity;

        if(username == null)
        {
            return new ResponseEntity<>("Unauthorized Acccee", HttpStatus.UNAUTHORIZED);
        }
        try {
            uploadLocationContextService.deleteUploadLocationContextById(id);
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
class UploadLocationContextBean implements Serializable {
    private UploadLocationContext uploadLocationContext;
    private String errorMessage;

    public void setUploadLocationContext(UploadLocationContext uploadLocationContext){
        this.uploadLocationContext = uploadLocationContext;
    }
    public UploadLocationContext getUploadLocationContext(){
        return this.uploadLocationContext;
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }

    @Override
    public String toString() {
        return "UploadLocationContextBean{" +
                "uploadLocationContext=" + uploadLocationContext +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}


