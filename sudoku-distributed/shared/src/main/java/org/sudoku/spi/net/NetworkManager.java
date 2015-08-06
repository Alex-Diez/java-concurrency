package org.sudoku.spi.net;

import java.io.IOException;

public interface NetworkManager
        extends AutoCloseable {

    String readFromNetwork()
            throws IOException;

    void writeToNetwork(String message)
            throws IOException;

}
