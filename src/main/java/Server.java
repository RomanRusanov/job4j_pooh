import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 15.10.2020
 * email roman9628@gmail.com
 * The class .
 */
public class Server {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Server.class.getName());
    /**
     * The field contain thread pool instance.
     */
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() - 1);

    private final Map<String, MessageHandler> messageHandlers = new HashMap<>();

    public Server() {
        this.fillMessageHandlers();
    }

    private void fillMessageHandlers() {
        this.messageHandlers.put("queue", new MessageHandlerQueue());
        this.messageHandlers.put("topic", new MessageHandlerTopic());
    }

    public InetAddress getAddress(){
        return InetAddress.getLoopbackAddress();
    }

    public ServerSocket getFreeSocket() {
        ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress(this.getAddress(),0));
            } catch (IOException e) {
                LOG.error("Can't create ServerSocket on port:" + serverSocket.getLocalPort(), e);
            }
        return serverSocket;
    }

    public int getPort() {
        ServerSocket serverSocket = this.getFreeSocket();
        int port = serverSocket.getLocalPort();
        pool.execute(() -> {
            this.getMessageAndReply(serverSocket);
            LOG.info("message apply");
        });
        return port;
    }

    public void getMessageAndReply(ServerSocket serverSocket) {
        String stringFromClient = null;
        try (
                ServerSocket server = serverSocket;
                Socket socket = server.accept())
        {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))) {
                stringFromClient = in.lines().collect(Collectors.joining());
            } catch (IOException e) {
                LOG.error("Get input stream.", e);
            }
            JSONParser jsonParser = new JSONParser();
            Message messageFromClient = jsonParser.handleJSON(stringFromClient);
            if (!this.messageHandlers.containsKey(messageFromClient.getTypeSequence())) {
                LOG.error(String.format(
                        "Unavailable json message type:(%s)",
                        messageFromClient.getTypeSequence()));
            }
            if (messageFromClient.getMessageType().equals("post")) {
                this.messageHandlers
                        .get(messageFromClient.getTypeSequence())
                        .post(messageFromClient);
            }
            if (messageFromClient.getMessageType().equals("get")) {
                Message send = this.messageHandlers
                        .get(messageFromClient.getTypeSequence())
                        .get(messageFromClient);
                try (PrintWriter out = new PrintWriter(
                        new BufferedOutputStream(socket.getOutputStream()))) {
                    out.print(send.getJSON());
                }
            }
        } catch (IOException e) {
            LOG.error("Get socket from instance server.", e);
        }
    }

    /**
     * The method shutdown Thread pool.
     */
    public void close() {
        this.pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}