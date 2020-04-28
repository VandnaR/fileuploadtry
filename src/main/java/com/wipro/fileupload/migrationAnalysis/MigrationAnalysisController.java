package com.wipro.fileupload.migrationAnalysis;

import com.wipro.fileupload.entities.UploadLocationContext;
import com.wipro.fileupload.migrationAnalysis.dto.ContextDTO;
import com.wipro.fileupload.uploadLocationContext.service.IUploadLocationContextService;
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
public class MigrationAnalysisController {

    @Autowired
    IUploadLocationContextService uploadLocationContextService;

    @PostMapping(value = "/migrationAnalysis", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResponseEntity<MigrationAnalysisBean> migrationAnalysisTool(@RequestBody ContextDTO contextDTO,
                                                                @RequestHeader("username") String username
    ) {
        MigrationAnalysisBean migrationAnalysisBean = new MigrationAnalysisBean();
        ResponseEntity<MigrationAnalysisBean> responseEntity;
        if (username == null) {
            migrationAnalysisBean.setErrorMessage("Unauthorized Access");
            responseEntity = new ResponseEntity<>(migrationAnalysisBean, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        try {

            UploadLocationContext uploadLocationContextEntity=uploadLocationContextService.getUploadLocationContextById(contextDTO.getUploadLocationContextId());
            String subFolder=contextDTO.getSubFolder();
            String fileName=contextDTO.getFileName();
            String source=contextDTO.getSource();
            String target=contextDTO.getTarget();

            //decode the access key and access secret key
            String accessKey=new String(Base64.getDecoder().decode(uploadLocationContextEntity.getAccessKey()));
            String accessSecretKey=new String(Base64.getDecoder().decode(uploadLocationContextEntity.getAccessSecretKey()));

            String clientRegion=uploadLocationContextEntity.getClientRegion();


            System.out.println("/opt/tomcat/RHAMT_Analyze.sh "+subFolder+" "+fileName+" "+accessKey+" "+accessSecretKey+" "+clientRegion+" "+source+" "+target);

            ProcessBuilder processBuilder = new ProcessBuilder("/opt/tomcat/RHAMT_Analyze.sh",subFolder,fileName,accessKey,accessSecretKey,clientRegion,source,target);
            Process process = processBuilder.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s="";
            System.out.println("ECHOOOOOOOO");

            if ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println("ECHOOOOOOOO Ends");

            Thread.sleep(1*60 *1000);

            migrationAnalysisBean.setReportUrl("https://s3.amazonaws.com/rhamt-reports/"+subFolder+"/report/index.html");
            responseEntity = new ResponseEntity<>(migrationAnalysisBean, HttpStatus.OK);

        } catch (HibernateException ex) {
            migrationAnalysisBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(migrationAnalysisBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            migrationAnalysisBean.setErrorMessage(ex.getMessage());
            responseEntity = new ResponseEntity<>(migrationAnalysisBean, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}

@XmlRootElement
class MigrationAnalysisBean implements Serializable {
    private String reportUrl;
    private String errorMessage;

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "MigrationAnalysisBean{" +
                "reportUrl='" + reportUrl + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
