package com.twuc.wf.twspring.simplehttpserver.utils;

import com.twuc.wf.twspring.simplehttpserver.contract.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class HttpRequestBuilder {


    public static HttpRequest buildHttpRequest(byte[] bytes) throws IOException {
        String httpRequestStr = new String(bytes);
        Reader reader = new StringReader(httpRequestStr);
        BufferedReader bufferedReader = new BufferedReader(reader);
        HttpRequest httpRequest = new HttpRequest();
        String line=bufferedReader.readLine();
        httpRequest.setStartLine(line);
        while((line = bufferedReader.readLine())!=null){
            line = line.trim();
            if("".equals(line)){
                break;
            }else {
                httpRequest.addHeader(line.split(":")[0], line.split(":")[1]);
            }
        }
        httpRequest.setContent(bufferedReader.readLine());

        return httpRequest;
    }
}
