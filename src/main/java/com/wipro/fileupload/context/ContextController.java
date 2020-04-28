package com.wipro.fileupload.context;

import com.wipro.fileupload.entities.Context;
import com.wipro.fileupload.context.service.IContextService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;

@Controller
public class ContextController {

    @Autowired
    IContextService contextService;

    @GetMapping(value = "/context/{id}", produces = {"application/json", "application/xml"} )
    public @ResponseBody ResponseEntity<ContextBean> getContextById(@PathVariable("id")Long id,
                                                                    @RequestHeader("username") String username) {

        ContextBean contextBean = new ContextBean();
        ResponseEntity<ContextBean> responseEntity;
        if(username == null)
        {
            contextBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        try {
            Context context = contextService.getContextById(id);
            contextBean.setContext(context);
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            contextBean.setErrorMessage(ex.getMessage());
            ex.printStackTrace();
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.NOT_FOUND);
        } catch (HibernateException ex) {
            contextBean.setErrorMessage(ex.getMessage());
            ex.printStackTrace();
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            contextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping(value = "/context", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<ContextBean> addContext(@RequestBody Context context,
                                                                @RequestHeader("username") String username){
        ContextBean contextBean = new ContextBean();
        ResponseEntity<ContextBean> responseEntity;
        if(username == null)
        {
            contextBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        try {
            context.setCreatedBy(username);

            //generating client id and secret
            context.setClientAppId(UUID.randomUUID().toString());
            context.setClientAppSecret(UUID.randomUUID().toString());

            Context contextReturned = contextService.addContext(context);
            contextBean.setContext(contextReturned);
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.OK);
        } catch (IllegalAccessException ex) {
            contextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.NOT_ACCEPTABLE);
        } catch (HibernateException ex) {
            contextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            contextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping(value = "/context/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<ContextBean> updateContext(@RequestBody Context context,
                                                                   @PathVariable("id")Long id,
                                                                   @RequestHeader("username") String username){
        ContextBean contextBean = new ContextBean();
        ResponseEntity<ContextBean> responseEntity;

        if(username == null || (context.getContextClientId() != null  && !context.getContextClientId().equals(id)))
        {
            contextBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }

        try {

            context.setUpdatedBy(username);
            Context contextReturned = contextService.updateContext(id, context);
            contextBean.setContext(contextReturned);
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.OK);

        } catch (EntityNotFoundException ex) {

            contextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.NOT_FOUND);

        } catch (HibernateException ex) {

            contextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {

            ex.printStackTrace();
            contextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(contextBean, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;
    }

    @DeleteMapping(value = "/context/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<String> deleteUserRecords(
            @PathVariable("id")Long id,
            @RequestHeader("username") String username){
        ResponseEntity<String> responseEntity;

        if(username == null)
        {
            return new ResponseEntity<>("Unauthorized Acccee", HttpStatus.UNAUTHORIZED);
        }
        try {
            contextService.deleteContextById(id);
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
class ContextBean implements Serializable {
    private Context context;
    private String errorMessage;

    public void setContext(Context context){
        this.context = context;
    }
    public Context getContext(){
        return this.context;
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }

    @Override
    public String toString() {
        return "ContextBean{" +
                "context=" + context +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}


