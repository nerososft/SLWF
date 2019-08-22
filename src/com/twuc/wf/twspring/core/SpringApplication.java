package com.twuc.wf.twspring.core;

import com.twuc.wf.twspring.simplehttpserver.IServer;
import com.twuc.wf.twspring.simplehttpserver.SimpleHttpServer;

import java.io.IOException;

import static com.twuc.wf.twspring.constant.CONSTANT.VERSION;


public class SpringApplication {

    private int HTTP_LISTEN_PORT = 8080;

    private AnnotationApplicationContext context;

    public SpringApplication() {
    }

    public void run(Class<?> contextClz){
        printCoolStartArt();

        context = new AnnotationApplicationContext(contextClz);

        try {
            startHttpListener();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printCoolStartArt() {
        System.out.println(" _____               _____               _               \n" +
                        "|_   _|             /  ___|             (_)              \n" +
                        "  | |  __      __   \\ `--.  _ __   _ __  _  _ __    __ _ \n" +
                        "  | |  \\ \\ /\\ / /    `--. \\| '_ \\ | '__|| || '_ \\  / _` |\n" +
                        "  | |   \\ V  V /  _ /\\__/ /| |_) || |   | || | | || (_| |\n" +
                        "  \\_/    \\_/\\_/  (_)\\____/ | .__/ |_|   |_||_| |_| \\__, |\n" +
                        "                           | |                      __/ |\n" +
                        "                           |_|                     |___/ \n"+
                VERSION+"\n");
    }

    private void startHttpListener() throws IOException {
        IServer server = new SimpleHttpServer(HTTP_LISTEN_PORT,context);
        server.start();
    }
}
