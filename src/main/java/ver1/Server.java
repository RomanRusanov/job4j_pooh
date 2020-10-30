package ver1;

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

    private final ServerSocket serverSocket;

    private volatile boolean isShutdown = false;

    public Server() {
        this.fillMessageHandlers();
        this.serverSocket = this.createServerSocket();
    }

    private void fillMessageHandlers() {
        this.messageHandlers.put("queue", new MessageHandlerQueue());
        this.messageHandlers.put("topic", new MessageHandlerTopic());
    }

    public InetAddress getAddress(){
        return InetAddress.getLoopbackAddress();
    }

    public ServerSocket createServerSocket() {
        ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(9000);
            } catch (IOException e) {
                LOG.error("Can't create ServerSocket on port:" + serverSocket.getLocalPort(), e);
            }
        return serverSocket;
    }

    public int getPort() {
        int port = serverSocket.getLocalPort();
        return port;
    }

    public void shutdown() {
        this.isShutdown = true;
    }


    public void newConnection() {
        pool.execute(() -> {
            LOG.info("ver1.Server wait connection");
            try {
                Socket socket = this.serverSocket.accept();
                LOG.info("ver1.Server connected client to port:" + socket.getPort());
                this.getMessageAndReply(socket);
            } catch (IOException e) {
                LOG.error("Can't create socket for new connection!");
            }
        });
    }

    public void getMessageAndReply(Socket socket) {
        String stringFromClient = "";
        try  {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                stringFromClient = in.lines().collect(Collectors.joining());
        } catch (IOException e) {
            LOG.error("Get socket from instance ServerMini.", e);
        }
        LOG.info("ver1.Server receive: " + stringFromClient);
        LOG.info("ver1.Server receive to port: " + socket.getPort());
        JSONParser jsonParser = new JSONParser();
        Message messageFromClient = null;
        try {
            messageFromClient = jsonParser.handleJSON(stringFromClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.info("Create message instance: " + messageFromClient);
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
            LOG.info("Section get to send");
            Message send = this.messageHandlers
                    .get(messageFromClient.getTypeSequence())
                    .get(messageFromClient);
            try (PrintWriter out = new PrintWriter(
                    new BufferedOutputStream(socket.getOutputStream()), true)){
                out.println(send.getJSON());
                out.flush();
                LOG.info("ver1.Server send JSON:" + send);
                LOG.info("ver1.Server send ToPort:" + socket.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOG.info("-DONE-");
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

    public Map<String, MessageHandler> getMessageHandlers() {
        return this.messageHandlers;
    }
}