import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 02.11.2020
 * email roman9628@gmail.com
 * The class .
 */
public class Client {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Client.class.getName());

    public String sendToServer(String sendToServer) {
        StringBuilder result = new StringBuilder();
        try(Socket socket = new Socket("localhost", 9000)) {
            Scanner scanner = new Scanner(socket.getInputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.print(sendToServer);
            LOG.info("CLIENT send: " + sendToServer);
            writer.flush();
            socket.shutdownOutput();
            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.info("CLIENT receive: " + result);
        return result.toString();
    }
}