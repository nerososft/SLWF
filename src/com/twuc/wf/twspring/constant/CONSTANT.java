package com.twuc.wf.twspring.constant;

import java.util.Date;

public class CONSTANT {
    public static final String HTTP_SERVER_LISTEN_ON_PORT = "TwSpring SimpleHttpServer started on port(s): %s (http)";
    public static final String VERSION = ":: ThoughtWorks University (China) Spring Boot ::     (v0.1.1.ALPHA)";

    public static void pInfo(Object info) {
        System.out.println("[" + new Date().toString() + "] " + info.toString());
    }
}

