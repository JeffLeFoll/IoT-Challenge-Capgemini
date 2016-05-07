package info.lefoll.socle.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import iot.challenge.application.injection.ApplicationModule;
import net.codestory.http.WebServer;
import net.codestory.http.extensions.Extensions;
import net.codestory.http.injection.GuiceAdapter;
import net.codestory.http.misc.Env;
import net.codestory.http.routes.Routes;

import java.nio.file.Paths;
import java.util.Optional;


public abstract class Serveur {

    private WebServer webServer;

    public void démarrer() {
        webServer = new WebServer();

        webServer.configure(this::configurerServeur);

        démarrerServeur();
    }

    private void configurerServeur(Routes routes) {
        routes.setIocAdapter(new GuiceAdapter(new ApplicationModule()));

        ajouterExtension(routes);

        configurerRoutes(routes);
    }

    private void ajouterExtension(Routes routes) {
        routes.setExtensions(new Extensions() {
            @Override
            public ObjectMapper configureOrReplaceObjectMapper(ObjectMapper defaultObjectMapper, Env env) {

                defaultObjectMapper.registerModule(new JavaTimeModule());

                return defaultObjectMapper;
            }
        });
    }

    private void démarrerServeur() {
        if (estEnModeHTTPS()) {
            démarrerEnHTTPS();
        } else {
            démarrerEnHTTP();
        }
    }

    private boolean estEnModeHTTPS() {
        String modeDeConnexion = Optional.ofNullable(System.getenv("net")).orElse("http");

        return modeDeConnexion.equalsIgnoreCase("https");
    }

    private void démarrerEnHTTP() {
        webServer.start(80);
    }

    private void démarrerEnHTTPS() {
        webServer.startSSL(9443, Paths.get("server.crt"), Paths.get("server.der"));
    }

    protected abstract void configurerRoutes(Routes routes);

}
