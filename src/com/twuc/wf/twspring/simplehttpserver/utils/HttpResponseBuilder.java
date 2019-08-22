package com.twuc.wf.twspring.simplehttpserver.utils;

import com.twuc.wf.twspring.simplehttpserver.contract.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class HttpResponseBuilder {


    public static String buildHttpResponse(HttpResponse httpResponse) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(httpResponse.getStartLine());
        for(Map.Entry<String,String> entry:httpResponse.getHeader().entrySet()){
            stringBuilder.append("\"");
            stringBuilder.append(entry.getKey());
            stringBuilder.append("\": \"");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\"\n");
        }
        stringBuilder.append("\r\n");
        stringBuilder.append(httpResponse.getContent());
        return stringBuilder.toString();
    }
}
