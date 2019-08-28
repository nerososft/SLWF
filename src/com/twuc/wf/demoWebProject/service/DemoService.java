package com.twuc.wf.demoWebProject.service;

import com.twuc.wf.demoWebProject.client.UserClient;
import com.twuc.wf.demoWebProject.entity.User;
import com.twuc.wf.demoWebProject.repo.DemoRepo;
import com.twuc.wf.twspring.annotations.Autowried;
import com.twuc.wf.twspring.annotations.Component;

@Component
public class DemoService {

    private final DemoRepo demoRepo;
    private final UserClient userClient;

    @Autowried
    public DemoService(DemoRepo demoRepo, UserClient userClient) {
        this.demoRepo = demoRepo;
        this.userClient = userClient;
    }

    public DemoRepo getDemoRepo() {
        return demoRepo;
    }

    public User getUserByFeign(String name) {
        return userClient.getUserByName(name);
    }
}
