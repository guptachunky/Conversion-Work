package com.conversion.Conversion.Utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CommonResponse {
    private String message;
    private Object data;
    HttpStatus code;

    public static CommonResponse success(String message, Object data) {
        CommonResponse response = new CommonResponse();
        response.setData(data);
        response.setMessage(message != null ? message : "SUCCESS");
        response.setCode(HttpStatus.OK);
        return response;
    }

    public static CommonResponse badRequest(String message, Object data) {
        CommonResponse response = new CommonResponse();
        response.setData(data);
        response.setMessage(message != null ? message : "Invalid Request");
        response.setCode(HttpStatus.BAD_REQUEST);
        return response;
    }

    public static CommonResponse badRequest(String message) {
        CommonResponse response = new CommonResponse();
        response.setData(null);
        response.setMessage(message != null ? message : "Invalid Request");
        response.setCode(HttpStatus.BAD_REQUEST);
        return response;
    }

    public static CommonResponse error(String message, Object data) {
        CommonResponse response = new CommonResponse();
        response.setData(data);
        response.setMessage(message != null ? message : "SUCCESS");
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }

    public static CommonResponse pageNotFound() {
        CommonResponse response = new CommonResponse();
        response.setCode(HttpStatus.NOT_FOUND);
        return response;
    }

    public static CommonResponse forbidden() {
        CommonResponse response = new CommonResponse();
        response.setCode(HttpStatus.FORBIDDEN);
        return response;
    }

    public static CommonResponse notAllowed() {
        CommonResponse response = new CommonResponse();
        response.setCode(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        return response;
    }

    public static CommonResponse multiStatus(String message) {
        CommonResponse response = new CommonResponse();
        response.setCode(HttpStatus.MULTI_STATUS);
        response.setMessage(message != null ? message : "Already Present");
        return response;
    }

    public static CommonResponse inUse(String message) {
        CommonResponse response = new CommonResponse();
        response.setCode(HttpStatus.IM_USED);
        response.setMessage(message != null ? message : "Already In Use");
        return response;
    }

    public static CommonResponse redirect(String message) {
        CommonResponse response = new CommonResponse();
        response.setCode(HttpStatus.TEMPORARY_REDIRECT);
        response.setMessage(message);
        return response;
    }


}
