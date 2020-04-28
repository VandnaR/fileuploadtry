package com.wipro.fileupload.feignproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Created by DE393632 on 1/17/2019.
 */
@FeignClient(name="${auth.service.name}", url="${auth.server.url}")
public interface FileUploadProxy {

    @PostMapping(value = "/oauth/validateToken", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TokenDTO> validateToken(@RequestBody ClientPayload clientPayload,
                                                  @RequestHeader("service_app_id") String serviceAppId,
                                                  @RequestHeader("service_app_secret") String serviceAppSecret);
}
