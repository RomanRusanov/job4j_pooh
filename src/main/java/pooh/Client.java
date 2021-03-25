package pooh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 02.11.2020
 * email roman9628@gmail.com
 * The class implements Publisher or Subscriber.
 */
public class Client {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Client.class.getName());

    /**
     * The method send string to server and take response from server.
     * Publisher or Subscriber bellow from JSON context.
     * @param sendToServer String in json format.
     * @return String in json format.
     */
    public String sendToServer(String sendToServer) {
        StringBuilder result = new StringBuilder();
        try (Socket socket = new Socket("127.0.0.1", 50500)) {
            Scanner scanner = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
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