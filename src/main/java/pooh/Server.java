package pooh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    /**
     * The field contain instance serverSocket.
     */
    private final ServerSocket serverSocket;
    /**
     * The field if true when pool create instance for client connections.
     */
    private volatile boolean work = true;
    /**
     * The collection contain handler for different message type.
     */
    private final Map<String, MessageHandler> messageHandlers = new HashMap<>();
    /**
     * The field contain thread pool instance.
     */
    private final ExecutorService pool = Executors
            .newFixedThreadPool(1);
    /**
     * The instance parse string to json, and make from json message instance.
     */
    private final JSONParser jsonParser = new JSONParser();

    /**
     * The default constructor.
     * @param serverPort port that wait client connection.
     */
    public Server(int serverPort) {
        this.serverSocket = this.initSocket(serverPort);
        this.fillMessageHandlers();
    }

    /**
     * Fill handlers collection.
     */
    private void fillMessageHandlers() {
        this.messageHandlers.put("queue", new MessageHandlerQueue());
        this.messageHandlers.put("topic", new MessageHandlerTopic());
    }

    /**
     * Init ServerSocket instance.
     * @param port number port.
     * @return Server socket.
     */
    private ServerSocket initSocket(int port) {
        ServerSocket result = null;
        try {
            result = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * The method create thread that accept new connection,
     * and process them.
     */
    public void acceptConnections() {
        while (work) {
            pool.execute(() -> {
                try (Socket socket = this.serverSocket.accept()) {
                    this.processConnection(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * The mwthod process connection. Receive string from client, parse json,
     * create instance message and response client.
     * @param incoming Client socket instance.
     */
    private void processConnection(Socket incoming) {
        try (OutputStream out = incoming.getOutputStream();
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(incoming.getInputStream()))) {
            String strFromClient = in.readLine();
            LOG.info("SERVER receive: " + strFromClient);
            Message message = jsonParser.handleJSON(strFromClient);
            LOG.info("SERVER create instance message: " + message);
            if (message.getMessageType().equals("post")) {
                this.messageHandlers
                        .get(message.getTypeSequence())
                        .post(message);
                out.write("POST/OK".getBytes());
            }
            if (message.getMessageType().equals("get")) {
                Message send = this.messageHandlers
                        .get(message.getTypeSequence())
                        .get(message);
                out.write(send.getJSON().getBytes());
                LOG.info("SERVER send: " + strFromClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method shutdown server.
     */
    public void shutdown() {
        this.work = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            LOG.error("Can't close ServerSocket", e);
        }
        this.pool.shutdown();
        while (!this.pool.isShutdown()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.info("Server down!");
    }
}