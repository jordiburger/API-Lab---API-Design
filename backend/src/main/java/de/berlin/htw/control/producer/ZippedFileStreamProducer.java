package de.berlin.htw.control.producer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import de.berlin.htw.control.qualifier.Zipped;

@Singleton
public class ZippedFileStreamProducer {

    @Zipped
    @Produces
    public OutputStream getStream() {
        return stream;
    }

    private ZipOutputStream stream;

    @PostConstruct
    private void openStream() throws IOException {
        Path temp = Files.createTempFile("va", ".zip");
        stream = new ZipOutputStream(Files.newOutputStream(temp));
    }

    @PreDestroy
    private void closeStream() throws IOException {
        stream.close();
    }
}
