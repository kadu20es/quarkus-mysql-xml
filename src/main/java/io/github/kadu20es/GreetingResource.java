package io.github.kadu20es;

import io.github.kadu20es.model.Cliente;
import io.github.kadu20es.service.ClienteService;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.logging.Logger;

@Path("/hello")
public class GreetingResource {

    private final ClienteService clienteService;

    public GreetingResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    void onStart(@Observes StartupEvent ev) {
        System.out.println("Aplicação iniciada.");

        try {
            clienteService.processarClientesAlterados();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
