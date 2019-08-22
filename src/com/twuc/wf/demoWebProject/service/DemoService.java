package com.twuc.wf.demoWebProject.service;

import com.twuc.wf.demoWebProject.repo.DemoRepo;
import com.twuc.wf.twspring.annotations.Autowried;
import com.twuc.wf.twspring.annotations.Component;

@Component
public class DemoService {

    private DemoRepo demoRepo;

    @Autowried
    public DemoService(DemoRepo demoRepo) {
        this.demoRepo = demoRepo;
    }

    public DemoRepo getDemoRepo() {
        return demoRepo;
    }
}
