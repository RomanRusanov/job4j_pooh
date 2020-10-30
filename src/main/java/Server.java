import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

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
        InputStream inputStream = null;
        try {
            inputStream = incoming.getInputStream();
            OutputStream outputStream = incoming.getOutputStream();
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)){
                PrintWriter printWriter = new PrintWriter(
                        new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
                printWriter.println("Hi! Enter BYE to exit.");
                /**
                 * передать обратно данные полученные от клиента
                 */
                boolean done = false;
                while (!done && scanner.hasNextLine()) {
                    String line = scanner.next();
                    printWriter.println("Echo: " + line);
                    if (line.trim().equals("BYE")) {
                        done = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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