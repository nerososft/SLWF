package com.twuc.wf.twspring.handler;

import com.twuc.wf.twspring.simplehttpserver.contract.HttpRequest;
import com.twuc.wf.twspring.simplehttpserver.contract.HttpResponse;
import com.twuc.wf.twspring.simplehttpserver.contract.HttpStatus;

import java.util.Map;

public class HttpResponseExceptionHandler {

    public HttpResponse handleExceptionInternal(Exception ex, String body, Map<String,String> header, HttpStatus status, HttpRequest request){
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatus(status.getValue());
        httpResponse.setPhrase(status.getReasonPhrase());
        httpResponse.setHeader(header);
        httpResponse.addHeader("Content-Type","text/html; charset=utf-8");
        httpResponse.addHeader("Content-Length", String.valueOf(body.length()));
        httpResponse.setContent(body);

        return httpResponse;
    }
}
