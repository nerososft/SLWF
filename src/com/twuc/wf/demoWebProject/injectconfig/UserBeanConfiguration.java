package com.twuc.wf.demoWebProject.injectconfig;

import com.twuc.wf.demoWebProject.entity.User;
import com.twuc.wf.twspring.annotations.Bean;
import com.twuc.wf.twspring.annotations.Configuration;

@Configuration
public class UserBeanConfiguration {

    @Bean
    public  User getUser(){
        return new User("yang",18);
    }
}
