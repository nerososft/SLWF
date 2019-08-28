package com.twuc.wf.twspring.simplehttpserver;


import com.twuc.wf.twspring.core.AnnotationApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.twuc.wf.twspring.constant.CONSTANT.HTTP_SERVER_LISTEN_ON_PORT;
import static com.twuc.wf.twspring.constant.CONSTANT.pInfo;

public class SimpleHttpServer implements IServer {
    private int port;
    private AnnotationApplicationContext context;

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ServerSocket serverSocket;

    public SimpleHttpServer(int http_listen_port, AnnotationApplicationContext context) throws IOException {
        this.port = http_listen_port;
        this.context  = context;
        serverSocket = new ServerSocket(this.port);
    }


    @Override
    public void start() throws IOException {
        pInfo("[ "+Thread.currentThread().getName()+" ] " + String.format(HTTP_SERVER_LISTEN_ON_PORT,this.port));
        while(!Thread.interrupted()){
            Socket socket = this.serverSocket.accept();
            executorService.execute(new SimpleHttpHandler(socket,context));
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void restart() {

    }
}
