import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 16.10.2020
 * email roman9628@gmail.com
 * The class .
 */
public class Publisher {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Publisher.class.getName());

    public void requestToServer(InetAddress address, int port, String json) {
        try (Socket socket = new Socket(address, port);
             PrintWriter out = new PrintWriter(
                     new BufferedOutputStream(socket.getOutputStream()))
        ) {
            out.print(json);
        } catch (IOException e) {
            LOG.error(String.format("Error! Not connected to server:%s port:%d",
                    address, port), e);
        }
    }
}