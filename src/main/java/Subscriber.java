import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 16.10.2020
 * email roman9628@gmail.com
 * The class .
 */
public class Subscriber {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Subscriber.class.getName());

    private final HashMap<String, String> replyFromServer = new HashMap<>();

    public void requestToServer(InetAddress address, int port, String json) {
        try (Socket socket = new Socket(address, port);
             PrintWriter out = new PrintWriter(
                     new BufferedOutputStream(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))
        ) {
            out.print(json);
            String stringFromServer = in.lines().collect(Collectors.joining());
            this.replyFromServer.put(json, stringFromServer);
        } catch (IOException e) {
            LOG.error(String.format("Error! Not connected to server:%s port:%d",
                    address, port), e);
        }
    }

}