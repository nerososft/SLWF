package com.twuc.wf.demoWebProject.service;

import com.twuc.wf.twspring.annotations.Component;
import com.twuc.wf.twspring.annotations.Scheduled;

@Component
public class ScheduleDemoService {

    @Scheduled(fixedRate = 5000)
    public void sayHelloEvery5Seconds(){
        System.out.println("hello, this is a interval schedule task!");
    }

    @Scheduled(initialDelay = 10000)
    public void sayHelloEveryAfter10Seconds(){
        System.out.println("hello, this is a timeout schedule task!");
    }
}
