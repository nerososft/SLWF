package com.twuc.wf.demoWebProject.repo;

import com.twuc.wf.demoWebProject.entity.User;
import com.twuc.wf.twspring.annotations.Autowried;
import com.twuc.wf.twspring.annotations.Component;

@Component
public class DemoRepo {
    private User user;

    @Autowried
    public DemoRepo(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
