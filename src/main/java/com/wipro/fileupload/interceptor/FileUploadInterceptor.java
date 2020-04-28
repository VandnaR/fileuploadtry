package com.wipro.fileupload.interceptor;

import com.wipro.fileupload.feignproxy.ClientPayload;
import com.wipro.fileupload.feignproxy.FileUploadProxy;
import com.wipro.fileupload.feignproxy.TokenDTO;
import com.wipro.fileupload.springConfig.ConfigUtility;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by DE393632 on 1/17/2019.
 */
@Component
public class FileUploadInterceptor implements HandlerInterceptor {

    @Autowired
    FileUploadProxy fileUploadProxy;

    @Autowired
    ConfigUtility configUtility;

    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("Pre Handle method of Interceptor called!!");
        String skipAuth=configUtility.getProperty("skipAuth");
        if(skipAuth.equals("false")){
            String serviceAppId=configUtility.getProperty("serviceAppId");
            String serviceAppSecret=configUtility.getProperty("serviceAppSecret");

            System.out.println("serviceAppId "+serviceAppId+"  "+"serviceAppSecret "+serviceAppSecret);

            String clientAppId=request.getHeader("clientAppId");
            String clientAppSecret=request.getHeader("clientAppSecret");
            String authorizationToken=request.getHeader("authorizationToken");
            String resourceRequestURL=request.getRequestURI();
            String methodType=request.getMethod();

            if(methodType.equals("OPTIONS"))
                return true;

            System.out.println("requested uri is "+request.getRequestURI());
            System.out.println("requested url is "+request.getRequestURL());


            String username=request.getHeader("username");

            ClientPayload clientPayload=new ClientPayload(clientAppId, clientAppSecret, authorizationToken, resourceRequestURL, methodType, username);
            //String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            /*String body="{\n" +
                    "\t\"clientAppId\":\"DMApp01\",\n" +
                    "\t\"clientAppSecret\":\"dm123456\",\n" +
                    "    \"authorizationToken\":\"dbef7b7c-d6a6-40fc-abe9-9eb5cc439863\",\n" +
                    "    \"resourceRequestURL\":\"http://localhost:8082/project\",\n" +
                    "    \"methodType\":\"POST\",\n" +
                    "    \"username\":\"vishal\"\n" +
                    "}";*/

            //converting the string body to Clientpayload pojo
            //ClientPayload clientPayload = new Gson().fromJson(body, ClientPayload.class);
            System.out.println(clientPayload);

            try{
                ResponseEntity<TokenDTO> tokenDTOResponseEntity = fileUploadProxy.validateToken(clientPayload, serviceAppId, serviceAppSecret);
                System.out.println(tokenDTOResponseEntity);
                if(tokenDTOResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
                    System.out.println("Accepted status recieved");
                    //response.setStatus(403);
                    return true;
                }
                else{
                    System.out.println("Forbidden");
                    return false;
                }
            }
            catch(FeignException ex){
                //ex.printStackTrace();
                System.out.println("other");
                response.setStatus(403);
                response.getWriter().write(ex.getMessage());
                return false;
            }
        }else{
            return true;
        }

    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        System.out.println("Post Handle method of Interceptor called!!");
    }
    @Override
    public void afterCompletion
            (HttpServletRequest request, HttpServletResponse response, Object
                    handler, Exception exception) throws Exception {

        System.out.println("Request and Response is completed");
    }

}
