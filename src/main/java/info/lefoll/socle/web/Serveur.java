package info.lefoll.socle.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import iot.challenge.application.injection.ApplicationModule;
import net.codestory.http.WebServer;
import net.codestory.http.extensions.Extensions;
import net.codestory.http.injection.GuiceAdapter;
import net.codestory.http.misc.Env;
import net.codestory.http.routes.Routes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Optional;


public abstract class Serveur {

    public void démarrer() {
        webServer = new WebServer()
        .withSelectThreads(2)
        .withThreadCount(8);

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
        LOGGER.info(String.format("démarrerEnHTTP sur le port %s", PORT_80));

        webServer.start(PORT_80);
    }

    private void démarrerEnHTTPS() {
        LOGGER.info(String.format("démarrerEnHTTPS sur le port %s", PORT_9443));

        webServer.startSSL(PORT_9443, Paths.get("server.crt"), Paths.get("server.der"));
    }

    protected abstract void configurerRoutes(Routes routes);

    private WebServer webServer;

    private static final int PORT_80 = 8090;
    private static final int PORT_9443 = 9443;

    private static Logger LOGGER = LoggerFactory.getLogger(Serveur.class);
}
