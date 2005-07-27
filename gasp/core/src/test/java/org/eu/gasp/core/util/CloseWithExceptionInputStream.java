package org.eu.gasp.core.util;


import java.io.IOException;
import java.io.InputStream;


public class CloseWithExceptionInputStream extends InputStream {
    @Override
    public int read() throws IOException {
        return 0;
    }


    @Override
    public void close() throws IOException {
        super.close();
        throw new IOException("You knew this exception would be thrown!");
    }
}
