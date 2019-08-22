package com.twuc.wf.twspring.simplehttpserver;

import java.io.IOException;

public interface IServer {
    void start() throws IOException;

    void stop();

    void restart();
}
