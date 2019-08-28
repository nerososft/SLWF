package com.twuc.wf.demoWebProject.web;

import com.twuc.wf.demoWebProject.entity.User;
import com.twuc.wf.demoWebProject.service.DemoService;
import com.twuc.wf.twspring.annotations.*;
import com.twuc.wf.twspring.simplehttpserver.contract.HttpStatus;
import com.twuc.wf.twspring.simplehttpserver.contract.RequestMethod;

@RestController("/api")
public class DemoController {

    private DemoService demoService;

    @Autowried
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String demoAction(@RequestParam("aaa") String name,
                             @RequestParam("bbb") String age){
//        throw new RuntimeException();
        boolean isDemoServiceNull = (demoService==null);
        boolean isDemoRepoNull = (null == demoService.getDemoRepo());
        boolean isDemoRepoUserNull = (null == demoService.getDemoRepo().getUser());
        return String.format("hello %s, seems you are %s years old. demoService:%s, demoRepo:%s, demoRepoName:%s, userName:%s, age:%s",
                name,age,
                isDemoServiceNull,isDemoRepoNull,isDemoRepoUserNull,
                demoService.getDemoRepo().getUser().getName(),
                demoService.getDemoRepo().getUser().getAge());
    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUserByName(@RequestParam("name") String name){
        return demoService.getUserByFeign(name);
    }
}
