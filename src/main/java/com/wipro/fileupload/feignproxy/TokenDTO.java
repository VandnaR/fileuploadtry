package com.wipro.fileupload.feignproxy;

/**
 * Created by DE393632 on 1/17/2019.
 */
public class TokenDTO {

    private Token token;
    private String errorMessage;


    public TokenDTO() {
    }

    public TokenDTO(Token token, String errorMessage) {
        this.token = token;
        this.errorMessage = errorMessage;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "TokenDTO{" +
                "token=" + token +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}

