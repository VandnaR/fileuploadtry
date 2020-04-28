package com.wipro.fileupload.entityContext;

import com.wipro.fileupload.entities.EntityContext;
import com.wipro.fileupload.entityContext.service.IEntityContextService;
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
public class EntityContextController {

    @Autowired
    IEntityContextService entityContextService;

    @GetMapping(value = "/entityContext/{id}", produces = {"application/json", "application/xml"} )
    public @ResponseBody ResponseEntity<EntityContextBean> getEntityContextById(@PathVariable("id")Long id,
                                                                    @RequestHeader("username") String username) {

        EntityContextBean entityContextBean = new EntityContextBean();
        ResponseEntity<EntityContextBean> responseEntity;
        if(username == null)
        {
            entityContextBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        try {
            EntityContext entityContext = entityContextService.getEntityContextById(id);
            entityContextBean.setEntityContext(entityContext);
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            entityContextBean.setErrorMessage(ex.getMessage());
            ex.printStackTrace();
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.NOT_FOUND);
        } catch (HibernateException ex) {
            entityContextBean.setErrorMessage(ex.getMessage());
            ex.printStackTrace();
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            entityContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping(value = "/entityContext", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<EntityContextBean> addEntityContext(@RequestBody EntityContext entityContext,
                                                                @RequestHeader("username") String username){
        EntityContextBean entityContextBean = new EntityContextBean();
        ResponseEntity<EntityContextBean> responseEntity;
        if(username == null)
        {
            entityContextBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        try {
            entityContext.setCreatedBy(username);
            EntityContext entityContextReturned = entityContextService.addEntityContext(entityContext);
            entityContextBean.setEntityContext(entityContextReturned);
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.OK);
        } catch (IllegalAccessException ex) {
            entityContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.NOT_ACCEPTABLE);
        } catch (HibernateException ex) {
            entityContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            entityContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping(value = "/entityContext/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<EntityContextBean> updateEntityContext(@RequestBody EntityContext entityContext,
                                                                   @PathVariable("id")Long id,
                                                                   @RequestHeader("username") String username){
        EntityContextBean entityContextBean = new EntityContextBean();
        ResponseEntity<EntityContextBean> responseEntity;

        if(username == null || (entityContext.getEntityContextId() != null  && !entityContext.getEntityContextId().equals(id)))
        {
            entityContextBean.setErrorMessage("Unauthorized access!!");
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }

        try {

            entityContext.setUpdatedBy(username);
            EntityContext entityContextReturned = entityContextService.updateEntityContext(id, entityContext);
            entityContextBean.setEntityContext(entityContextReturned);
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.OK);

        } catch (EntityNotFoundException ex) {

            entityContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.NOT_FOUND);

        } catch (HibernateException ex) {

            entityContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {

            ex.printStackTrace();
            entityContextBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(entityContextBean, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;
    }

    @DeleteMapping(value = "/entityContext/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<String> deleteUserRecords(
            @PathVariable("id")Long id,
            @RequestHeader("username") String username){
        ResponseEntity<String> responseEntity;

        if(username == null)
        {
            return new ResponseEntity<>("Unauthorized Acccee", HttpStatus.UNAUTHORIZED);
        }
        try {
            entityContextService.deleteEntityContextById(id);
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
class EntityContextBean implements Serializable {
    private EntityContext entityContext;
    private String errorMessage;

    public void setEntityContext(EntityContext entityContext){
        this.entityContext = entityContext;
    }
    public EntityContext getEntityContext(){
        return this.entityContext;
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }

    @Override
    public String toString() {
        return "EntityContextBean{" +
                "entityContext=" + entityContext +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}


