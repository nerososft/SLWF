package com.twuc.wf.twspring.simplehttpserver.contract;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String startLine;
    private String phrase = "OK";
    private int status = 200;
    private Map<String,String> header;
    private String content;

    public HttpResponse() {
        header = new HashMap<>();
    }

    public HttpResponse(String startLine) {
        this.startLine = startLine;
    }

    public HttpResponse(String startLine, Map<String, String> header, String content) {
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
        return "HttpResponse{" +
                "startLine='" + startLine + '\'' +
                ", header=" + header +
                ", content='" + content + '\'' +
                '}';
    }

    public void addHeader(String key, String value) {
        this.header.put(key,value);
    }

    public void setStatus(int status) {
        this.status = status;
        this.setStartLine(String.format("HTTP/1.1 %s %s\n",this.status,this.phrase));
    }


    public String getPhrase() {
        return phrase;
    }

    public int getStatus() {
        return status;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
        this.setStartLine(String.format("HTTP/1.1 %s %s\n",this.status,this.phrase));
    }
}
