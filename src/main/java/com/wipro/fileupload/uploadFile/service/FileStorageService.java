package com.wipro.fileupload.uploadFile.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.wipro.fileupload.entities.FileUpload;
import com.wipro.fileupload.entities.UploadLocationContext;
import com.wipro.fileupload.fileUpload.service.IFileUploadService;
import com.wipro.fileupload.springConfig.ConfigUtility;
import com.wipro.fileupload.uploadFile.exception.FileStorageException;
import com.wipro.fileupload.uploadFile.exception.MyFileNotFoundException;
import com.wipro.fileupload.uploadFile.property.FileStorageProperties;
import com.wipro.fileupload.uploadLocationContext.service.IUploadLocationContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    ConfigUtility configUtility;

    @Autowired
    IUploadLocationContextService uploadLocationContextService;

    @Autowired
    IFileUploadService fileUploadService;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public FileUpload uploadFileToAWS(MultipartFile file,String username,String subFolderPath,Long contextId,Long entityContextId,Long uploadLocationContextId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

           /* String accessKey=configUtility.getProperty("accessKey");
            String secretAccessKey=configUtility.getProperty("secretAccessKey");

            String clientRegion = configUtility.getProperty("clientRegion");
            String bucketName = configUtility.getProperty("bucketName");*/

            //get uploadLocationContext to get the destination_type details
            UploadLocationContext uploadLocationContextEntity=uploadLocationContextService.getUploadLocationContextById(uploadLocationContextId);

            //decode the access key and access secret key
            String accessKey= new String(Base64.getDecoder().decode(uploadLocationContextEntity.getAccessKey()));
            String secretAccessKey=new String(Base64.getDecoder().decode(uploadLocationContextEntity.getAccessSecretKey()));

            String clientRegion = uploadLocationContextEntity.getClientRegion();
            String bucketName = uploadLocationContextEntity.getBucketName();

            System.out.println(accessKey+" "+secretAccessKey+" "+clientRegion+" "+bucketName);
            //rhamt-reports/project/app/build_no/report/index.html
            //bucketName/projectName/appName/analysisNo
            String bucketUploadPath=bucketName+"/"+subFolderPath;

            System.out.println(bucketUploadPath);

            BasicAWSCredentials basicAWSCredentials=new BasicAWSCredentials(accessKey,secretAccessKey);

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();
            TransferManager tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .build();


            // TransferManager processes all transfers asynchronously,
            // so this call returns immediately.
            ObjectMetadata metadata=new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            metadata.setContentLength(bytes.length);

            Upload upload = tm.upload(bucketUploadPath,fileName,file.getInputStream(),metadata);
            //https://s3.amazonaws.com/rhamt-reports/
            System.out.println("Upload Description");
            System.out.println(upload.getDescription());
            System.out.println("Upload Description ends");
            System.out.println("Object upload started");

            // Optionally, wait for the upload to finish before continuing.
            upload.waitForCompletion();
            System.out.println("Object upload complete");

            FileUpload fileUpload=new FileUpload();
            fileUpload.setContextClientId(contextId);
            fileUpload.setEntityContextId(entityContextId);
            fileUpload.setUploadLocationContextId(uploadLocationContextId);
            fileUpload.setFileName(fileName);
            fileUpload.setCreatedBy(username);
            fileUpload.setCreatedOn(new Date());
            fileUpload.setFileUploadUrl(configUtility.getProperty("awsS3Url")+bucketUploadPath);
            FileUpload fileUploadEntity = fileUploadService.addFileUpload(fileUpload);

            return fileUploadEntity;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
