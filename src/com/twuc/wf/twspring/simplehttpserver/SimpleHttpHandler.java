package com.twuc.wf.twspring.simplehttpserver;

import com.twuc.wf.twspring.annotations.ExceptionHandler;
import com.twuc.wf.twspring.annotations.RequestParam;
import com.twuc.wf.twspring.annotations.ResponseStatus;
import com.twuc.wf.twspring.core.AnnotationApplicationContext;
import com.twuc.wf.twspring.entity.ClassMethodPair;
import com.twuc.wf.twspring.simplehttpserver.contract.HttpStatus;
import com.twuc.wf.twspring.simplehttpserver.contract.RequestMethod;
import com.twuc.wf.twspring.simplehttpserver.contract.HttpRequest;
import com.twuc.wf.twspring.simplehttpserver.contract.HttpResponse;
import com.twuc.wf.twspring.simplehttpserver.exceptions.BrokenHttpPackageExcepetion;
import com.twuc.wf.twspring.simplehttpserver.utils.HttpRequestBuilder;
import com.twuc.wf.twspring.simplehttpserver.utils.HttpResponseBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.Socket;
import java.util.*;

import static com.twuc.wf.twspring.constant.CONSTANT.pInfo;

public class SimpleHttpHandler implements Runnable {
    private Socket socket;
    private AnnotationApplicationContext context;
    private byte[] bytes;


    SimpleHttpHandler(Socket socket, AnnotationApplicationContext context) {
        this.socket = socket;
        this.context = context;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        ClassMethodPair routeEntity = null;
        HttpRequest httpRequest = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            bytes = new byte[1024];
            // 1. un-package http request package
            inputStream.read(bytes);
            if (null == bytes || "".equals(new String(bytes))) {
                throw new BrokenHttpPackageExcepetion();
            }
            httpRequest = HttpRequestBuilder.buildHttpRequest(bytes);

            pInfo("[ "+Thread.currentThread().getName()+" ] " + httpRequest.toString());

            RequestMethod method = httpRequest.getHttpMethod();
            String uri = httpRequest.getUri();
            String protocol = httpRequest.getProtocol();
            Map<String,String> queryParams = httpRequest.getQueryParams();

            // @PathVariable need implement
            // /api/yang/demo
            // /api/{test}/demo




            pInfo(String.format("[ %s Request ] %s",protocol,uri));

            // 2. get route entity
            // 2.1 get route entity from get method mapping
            if (method == RequestMethod.POST) {
                routeEntity = AnnotationApplicationContext.postMappingMap.get(uri);
            } else {
                routeEntity = AnnotationApplicationContext.getMappingMap.get(uri);
            }

            HttpResponse httpResponse = new HttpResponse();

            // if not found route entity, then set status to 404
            if(null == routeEntity){
                // 404
                response404(httpResponse);
            }else {

                // 3. get instance from factory
                Object o = context.getBean(routeEntity.getClz());

                // 4. get path variable and request params
                List<String> params = getRequestParams(routeEntity, queryParams);

                // 5. reflect invoke
                Object result = null;
                try {
                    result = routeEntity.getMethod().invoke(o, params.toArray());
                    pInfo("[ " + Thread.currentThread().getName() + " ] " + result.toString());
                }catch (Exception e){
                    e.printStackTrace();
                    Class<?> adviceClass = AnnotationApplicationContext.exceptionHandlerMap.get(routeEntity.getClz());
                    try {
                        Object adviceInstance  = adviceClass.newInstance();
                        for(Method adviceHandlerMethod : adviceClass.getMethods()){
                            ExceptionHandler exceptionHandler = adviceHandlerMethod.getDeclaredAnnotation(ExceptionHandler.class);
                            if(null != exceptionHandler){
                                Object[] args = new Object[2];
                                args[0] = e;
                                args[1] = httpRequest;
                                Object res = adviceHandlerMethod.invoke(adviceInstance,args);
                                String httpResponseStr = HttpResponseBuilder.buildHttpResponse((HttpResponse) res);
                                outputStream.write(httpResponseStr.getBytes());
                            }
                        }
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                }

                // set status
                setResponseStatus(routeEntity, httpResponse);
                setResponse(httpResponse, result);
            }

            pInfo("[ "+Thread.currentThread().getName()+" ] " + httpResponse.toString());

            String httpResponseStr = HttpResponseBuilder.buildHttpResponse(httpResponse);

            // 4. write http response
            outputStream.write(httpResponseStr.getBytes());

        } catch (Exception e) {
            // reflect a  HttpResponseExceptionHandler
            e.printStackTrace();
            assert routeEntity != null;
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setResponse(HttpResponse httpResponse, Object result) {
        httpResponse.addHeader("Server", "ThoughtWorksUniversity(China) Spring SimpleHttpServer");
        httpResponse.addHeader("Date", new Date().toString());
        httpResponse.addHeader("Content-Type", "text/html; charset=utf-8");
        httpResponse.addHeader("Content-Length", String.valueOf(result.toString().length()));
//                httpResponse.addHeader("Connection", "keep-alive");
//                httpResponse.addHeader("Keep-Alive", "timeout=20");
        httpResponse.setContent(result.toString());
    }

    private void setResponseStatus(ClassMethodPair routeEntity, HttpResponse httpResponse) {
        ResponseStatus responseStatus = routeEntity.getMethod().getDeclaredAnnotation(ResponseStatus.class);
        if(responseStatus!=null){
            httpResponse.setStatus(responseStatus.value().getValue());
            httpResponse.setPhrase(responseStatus.value().getReasonPhrase());
        }else {
            // 3. build http response package
            httpResponse.setStatus(HttpStatus.OK.getValue());
            httpResponse.setPhrase(HttpStatus.OK.getReasonPhrase());
        }
    }

    private List<String> getRequestParams(ClassMethodPair routeEntity, Map<String, String> queryParams) {
        Parameter[] parameters = routeEntity.getMethod().getParameters();
        List<String> params = new ArrayList<>();
        for (Parameter p : parameters) {
            RequestParam requestParam = p.getDeclaredAnnotation(RequestParam.class);
            if (null != requestParam) {
                String requestParamater= requestParam.value();
                // 4.1 get request paramater from http request, and set to invoke method' args
                // todo : rember paramater's type
                String paramsValue = queryParams.get(requestParamater);
                params.add(paramsValue);
            }
        }
        return params;
    }

    private void response404(HttpResponse httpResponse) {
        httpResponse.setStatus(404);
        httpResponse.setPhrase("NOT_FOUND");
        httpResponse.addHeader("Content-Type","text/html; charset=utf-8");
        String result = "404 not found";
        httpResponse.addHeader("Content-Length", String.valueOf(result.length()));
        httpResponse.setContent(result);
    }
}
