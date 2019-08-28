package com.twuc.wf.twspring.simplehttpserver.contract;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String startLine;
    private Map<String, String> header;
    private String content;

    public HttpRequest() {
        this.header = new HashMap<>();
    }

    public HttpRequest(String startLine, Map<String, String> header, String content) {
        this.startLine = startLine;
        this.header = header;
        this.content = content;
    }


    public String getStartLine() {
        return startLine;
    }

    public void setStartLine(String startLine) {
        this.startLine = startLine;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "startLine='" + startLine + '\'' +
                ", header=" + header +
                ", content='" + content + '\'' +
                '}';
    }

    public void addHeader(String key, String value) {
        this.header.put(key, value);
    }

    public RequestMethod getHttpMethod() {
        switch (this.startLine.split(" ")[0]) {
            case "PUT":
                return RequestMethod.PUT;
            case "POST":
                return RequestMethod.POST;
            case "DELETE":
                return RequestMethod.DELETE;
            default:
                return RequestMethod.GET;
        }
    }

    public String getUri() {
        return this.startLine.split(" ")[1].split("\\?")[0];
    }

    public Map<String, String> getQueryParams() {
        try {
            String queryString = this.startLine.split(" ")[1].split("\\?")[1];
            String[] queryPair = queryString.split("&");
            Map<String, String> queryParamsMap = new HashMap<>();
            for (String pair : queryPair) {
                queryParamsMap.put(pair.split("=")[0], pair.split("=")[1]);
            }
            return queryParamsMap;
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return new HashMap<>();
        }
    }

    public String getProtocol() {
        return this.startLine.split(" ")[2];
    }
}


