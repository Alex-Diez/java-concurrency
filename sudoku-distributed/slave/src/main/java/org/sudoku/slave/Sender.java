package org.sudoku.slave;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.sudoku.conf.SlaveStatus;
import org.sudoku.elements.Square;
import org.sudoku.spi.net.NetworkManager;

public class Sender {

    private final NetworkManager networkManager;
    private final ObjectMapper objectMapper;

    public Sender(NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.objectMapper = new ObjectMapper();
    }

    public void sendData(Square square)
            throws IOException {
        final String data = prepareToSend(square);
        networkManager.writeToNetwork(data);
    }

    public void sendStatus(SlaveStatus status)
            throws IOException {
        final String data = prepareToSend(status);
        networkManager.writeToNetwork(data);
    }

    private String prepareToSend(Object value)
            throws IOException {
        return objectMapper.writeValueAsString(value);
    }
}
