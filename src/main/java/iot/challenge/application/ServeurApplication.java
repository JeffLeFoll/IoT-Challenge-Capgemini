package iot.challenge.application;

import info.lefoll.socle.web.Serveur;
import iot.challenge.application.resources.Messages;
import net.codestory.http.routes.Routes;

public class ServeurApplication extends Serveur {

    public static void main(String[] args) {
        new ServeurApplication().démarrer();
    }

    @Override
    protected void configurerRoutes(Routes routes) {
        routes.get("/isAlive", "ok");
        routes.add(Messages.class);
    }

    private ServeurApplication() {
        super();
    }


}