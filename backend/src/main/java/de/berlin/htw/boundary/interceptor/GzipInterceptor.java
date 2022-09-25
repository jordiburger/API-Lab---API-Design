package de.berlin.htw.boundary.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

@Provider
public class GzipInterceptor implements ReaderInterceptor, WriterInterceptor {

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        // do nothing
    }

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context)
            throws IOException, WebApplicationException {
        String enc = context.getHeaders().getFirst("Content-Encoding");
        if (enc == null) {
            // no encoding on input stream
            return context.proceed();
        } else if (enc.equalsIgnoreCase("gzip")) {
            InputStream zip = context.getInputStream();
            context.setInputStream(new GZIPInputStream(zip));
            return context.proceed();
        } else {
            throw new NotAcceptableException("Invalid content encoding.");
        }
    }

}
