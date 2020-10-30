package ver1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
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

    private final ConcurrentLinkedQueue<String> replyFromServer = new ConcurrentLinkedQueue<String>();

    public void requestToServer(InetAddress address, int port, String json) {
        try (Socket socket = new Socket(address, port);
             PrintWriter out = new PrintWriter(
                     new BufferedOutputStream(socket.getOutputStream()), true)
        ) {

            out.println(json);
            LOG.info("ver1.Subscriber send: " +  json);
            LOG.info("ver1.Subscriber send to port: " +  socket.getPort());
        } catch (IOException e) {
            LOG.error(String.format("Error! Not connected to ServerMini:%s port:%d",
                    address, port), e);
        }
        new Thread(() -> {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(new Socket(InetAddress.getLoopbackAddress(), 9000).getInputStream()));
                String stringFromServer = in.lines().collect(Collectors.joining());
                this.replyFromServer.add(stringFromServer);
                LOG.info("ver1.Subscriber get response from ver1.Server: " + stringFromServer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }


    public ConcurrentLinkedQueue<String> getReplies() {
        return this.replyFromServer;
    }

}