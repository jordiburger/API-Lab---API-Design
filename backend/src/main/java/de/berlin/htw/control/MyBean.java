package de.berlin.htw.control;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.InternalServerErrorException;

import de.berlin.htw.control.qualifier.GZipped;
import io.quarkus.runtime.Startup;

@Startup
@Singleton
public class MyBean {
    
    @Inject
    @GZipped
    OutputStream stream;

    GreetingService service;

    @Inject
    private MyBean(GreetingService service) {
        super();
        this.service = service;
    }

    public String getText(final Principal userPrincipal) {
        final String text = service.getGeeting(userPrincipal) + "welcome!";
        try {
            stream.write(text.getBytes());
        } catch (IOException e) {
            throw new InternalServerErrorException("Unable to write text.", e);
        }
        return service.getGeeting(userPrincipal) + "welcome!";
    }
}
