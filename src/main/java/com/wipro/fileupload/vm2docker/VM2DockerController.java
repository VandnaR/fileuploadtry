package com.wipro.fileupload.vm2docker;

import com.wipro.fileupload.uploadLocationContext.service.IUploadLocationContextService;
import com.wipro.fileupload.vm2docker.dto.Vm2DockerDTO;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Base64;


@Controller
public class VM2DockerController {

    @Autowired
    IUploadLocationContextService uploadLocationContextService;

    @PostMapping(value = "/vm2docker", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResponseEntity<VM2DockerBean> vmToDockerImageConversion(@RequestBody Vm2DockerDTO vm2DockerDTO, @RequestHeader("username") String username) {
        VM2DockerBean vm2DockerBean = new VM2DockerBean();
        ResponseEntity<VM2DockerBean> responseEntity;
        if (username == null) {
            vm2DockerBean.setErrorMessage("Unauthorized Access");
            responseEntity = new ResponseEntity<>(vm2DockerBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        try {

            //in case access key and secret access key needed
            /*UploadLocationContext uploadLocationContextEntity=uploadLocationContextService.getUploadLocationContextById(contextDTO.getUploadLocationContextId());
            String subFolder=contextDTO.getSubFolder();
            String fileName=contextDTO.getFileName();

            //decode the access key and access secret key
            String accessKey=new String(Base64.getDecoder().decode(uploadLocationContextEntity.getAccessKey()));
            String accessSecretKey=new String(Base64.getDecoder().decode(uploadLocationContextEntity.getAccessSecretKey()));

            String clientRegion=uploadLocationContextEntity.getClientRegion();


            System.out.println("/opt/tomcat/RHAMT_Analyze.sh "+subFolder+" "+fileName+" "+accessKey+" "+accessSecretKey+" "+clientRegion);*/

            String s3Path=vm2DockerDTO.getS3Path();
            String appName=vm2DockerDTO.getAppName();
            String fileName=s3Path.substring(s3Path.lastIndexOf("/")+1);
            String repoName=vm2DockerDTO.getRepoName();


            System.out.println("file name is "+fileName);
            System.out.println("In vm2Docker method");

            ProcessBuilder processBuilder = new ProcessBuilder("/opt/tomcat/vmToDocker.sh",s3Path,appName,fileName,repoName);
            Process process = processBuilder.start();

//            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String s="";
//            System.out.println("ECHOOOOOOOO");
//
//            if ((s = stdInput.readLine()) != null) {
//                System.out.println(s);
//            }
//            System.out.println("ECHOOOOOOOO Ends");

            Thread.sleep(1*60 *1000);

            vm2DockerBean.setMessage("VM to Docker Image completed successfully");
            System.out.println("End of vm2Docker method");
            responseEntity = new ResponseEntity<>(vm2DockerBean, HttpStatus.OK);

        } catch (HibernateException ex) {
            vm2DockerBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(vm2DockerBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            vm2DockerBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(vm2DockerBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}

@XmlRootElement
class VM2DockerBean implements Serializable {
    private String message;
    private String errorMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "VM2DockerBean{" +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
