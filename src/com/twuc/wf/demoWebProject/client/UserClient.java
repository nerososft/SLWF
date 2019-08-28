package com.twuc.wf.demoWebProject.client;

import com.twuc.wf.demoWebProject.entity.User;
import com.twuc.wf.twspring.core.rpc.annotations.Feign;

@Feign("/api/")
public interface UserClient {
    User getUserByName(String name);
}
