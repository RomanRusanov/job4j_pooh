import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import java.util.stream.*;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 30.10.2020
 * email roman9628@gmail.com
 * The class .
 */
public class Server {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Server.class.getName());

    private final int serverPort;

    private final ServerSocket serverSocket;

    private volatile boolean work = true;

    public Server(int serverPort) {
        this.serverPort = serverPort;
        this.serverSocket = this.initSocket(this.serverPort);
    }

    private ServerSocket initSocket(int port) {
        ServerSocket result = null;
        try {
            result = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void getConnection() {
        while (work) {
            try (Socket socket = this.serverSocket.accept()){
                this.processConnection(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void processConnection(Socket incoming) {
        try (OutputStream out = incoming.getOutputStream();
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(incoming.getInputStream()))) {
            String str = in.readLine();
            LOG.info("SERVER receive: " + str);
            str += "MODIFIED!";
            out.write(str.getBytes());
            LOG.info("SERVER send: " + str);
        } catch (IOException e) {
            LOG.error("Error with socket work: " + e);
        }
    }

    public void shutdown() {
        this.work = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}