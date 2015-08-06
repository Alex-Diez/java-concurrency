package org.sudoku.slave;

import java.io.IOException;

import org.sudoku.conf.SlaveStatus;
import org.sudoku.slave.strategies.ResolverByBlock;
import org.sudoku.spi.net.NetworkManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slave {

    private static final Logger LOG = LoggerFactory.getLogger(Slave.class);

    private static final String DEFAULT_MASTER_HOST = "localhost";
    private static final int DEFAULT_MASTER_PORT = 10567;

    private final Sender sender;
    private final Receiver receiver;
    private SlaveStatus status;

    public Slave(final Receiver receiver, final Sender sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    public static void main(String[] args) {
        NetworkManager networkManager = null;
        try {
            final Connector connector = new Connector(DEFAULT_MASTER_PORT, DEFAULT_MASTER_HOST);
            networkManager = connector.connect();
        }
        catch (IOException e) {
            LOG.debug("IO while create connection to master");
            System.exit(0);
        }
        final Receiver receiver = new Receiver(networkManager);
        final Sender sender = new Sender(networkManager);
        new Slave(receiver, sender).start();
    }

    private void start() {
        status = SlaveStatus.READY_TO_SERVE;
//		sender.sendStatus(status);
//		retrieveNeededData
        ResolverByBlock resolverByBlock = null;
//		buildResolver();
        while (status != SlaveStatus.DONE) {
            status = resolverByBlock.execute();
            if (status == SlaveStatus.IDLE) {
//				takeDataFromMaster
            }
        }
    }
}
