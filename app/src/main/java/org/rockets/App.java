package org.rockets;

import org.rockets.api.IApiServer;
import org.rockets.module.RocketsModule;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class App {

    // Might want to make into a config in the future
    public static final int API_SERVER_PORT = 8088;

    private final IApiServer apiServer;

    @Inject
    public App(IApiServer apiServer) {
        this.apiServer = apiServer;
    }

    public void runApp() {
        apiServer.run(API_SERVER_PORT);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new RocketsModule());
        App app = injector.getInstance(App.class);
        app.runApp();
    }
}
