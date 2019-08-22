package com.twuc.wf.demoWebProject.exhandler;

import com.twuc.wf.demoWebProject.web.DemoController;
import com.twuc.wf.twspring.simplehttpserver.contract.HttpRequest;
import com.twuc.wf.twspring.simplehttpserver.contract.HttpResponse;
import com.twuc.wf.twspring.simplehttpserver.contract.HttpStatus;
import com.twuc.wf.twspring.annotations.ControllerAdvice;
import com.twuc.wf.twspring.annotations.ExceptionHandler;
import com.twuc.wf.twspring.handler.HttpResponseExceptionHandler;


@ControllerAdvice(basePackagesClasses = {DemoController.class})
public class MyExceptionHandler extends HttpResponseExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    public HttpResponse handleConflict(Exception ex, HttpRequest request) {
        return handleExceptionInternal(ex, "some thing error", request.getHeader(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
