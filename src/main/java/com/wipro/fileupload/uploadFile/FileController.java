package com.wipro.fileupload.uploadFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.fileupload.context.service.IContextService;
import com.wipro.fileupload.entities.Context;
import com.wipro.fileupload.entities.FileUpload;
import com.wipro.fileupload.uploadFile.payload.ContextDTO;
import com.wipro.fileupload.uploadFile.payload.UploadFileResponse;
import com.wipro.fileupload.uploadFile.service.FileStorageService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IContextService contextService;


    /*@PostMapping(value = "/uploadFile", produces = {"application/json", "application/xml"})
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.uploadFileToAWS(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }*/

    @PostMapping(value = "/uploadFile", produces = {"application/json", "application/xml"})
    public @ResponseBody ResponseEntity<FileUploadBean> uploadFile(@RequestParam("file") MultipartFile file,
                                         @RequestParam("contextModel" ) String contextModel,
                                         @RequestHeader("username") String username
                                         /*@RequestHeader("projectName") String projectName,
                                         @RequestHeader("appName") String appName,
                                         @RequestHeader("clientAppId") String clientAppId,
                                         @RequestHeader("clientAppSecret") String clientAppSecret,
                                         @RequestHeader("contextId") Long contextId,
                                         @RequestHeader("entityContextId") Long entityContextId,
                                         @RequestHeader("uploadLocationContextId") Long uploadLocationContextId*/) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ContextDTO modelDTO = mapper.readValue(contextModel, ContextDTO.class);

        FileUploadBean fileUploadBean=new FileUploadBean();
        ResponseEntity<FileUploadBean> responseEntity;
        //Context context=contextService.getContextByClientAppIdAndClientAppSecret(clientAppId,clientAppSecret);
        Context context=contextService.getContextByClientAppIdAndClientAppSecret(modelDTO.getClientAppId(),modelDTO.getClientAppSecret());

        try {
            //if client app id and client app secret is correct respective to the context id of header
            //if(context.getContextClientId().equals(contextId)){
            if(context.getContextClientId().equals(modelDTO.getContextClientId())){
                //FileUpload fileUploadEntity = fileStorageService.uploadFileToAWS(file,username,projectName,appName,contextId,entityContextId,uploadLocationContextId);
                FileUpload fileUploadEntity = fileStorageService.uploadFileToAWS(file,username,modelDTO.getSubFolderPath(),modelDTO.getContextClientId(),modelDTO.getEntityContextId(),modelDTO.getUploadLocationContextId());
                fileUploadBean.setFileUpload(fileUploadEntity);
                responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.OK);
            }
            else{
                fileUploadBean.setErrorMessage("Unauthorized access!!");
                responseEntity = new ResponseEntity<>(fileUploadBean, HttpStatus.UNAUTHORIZED);
                return responseEntity;
            }
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
        //String fileName = fileStorageService.uploadFileToAWS(file,projectName,appName,contextId,entityContextId,uploadLocationContextId);

        /*String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());*/
    }

   /* @PostMapping(value = "/uploadMultipleFiles", produces = {"application/json", "application/xml"})
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }*/

    @GetMapping(value = "/downloadFile/{fileName:.+}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}

@XmlRootElement
class FileUploadBean implements Serializable {
    private FileUpload fileUpload;
    private String errorMessage;

    public FileUpload getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ContextBean{" +
                "fileUpload=" + fileUpload +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
