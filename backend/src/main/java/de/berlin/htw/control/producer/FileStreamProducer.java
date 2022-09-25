package de.berlin.htw.control.producer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class FileStreamProducer {

    @Default
    @Produces
    public OutputStream getStream() {
        return stream;
    }

    private OutputStream stream;

    @PostConstruct
    private void openStream() throws IOException {
        Path temp = Files.createTempFile("va", ".file");
        stream = Files.newOutputStream(temp);
    }

    @PreDestroy
    private void closeStream() throws IOException {
        stream.close();
    }
}
