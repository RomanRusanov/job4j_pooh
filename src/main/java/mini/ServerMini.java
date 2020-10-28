package mini;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.stream.*;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 28.10.2020
 * email roman9628@gmail.com
 * The class .
 */
public class ServerMini {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ServerMini.class.getName());

    private ServerSocket serverSocket;

    public ServerMini() {
        this.init();
    }

    public void init() {
        try {
            this.serverSocket = new ServerSocket(9000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitConnectionAndGet() {
        new Thread(() -> {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connected!");
                this.receive(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void receive(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fromClient = reader.lines().collect(Collectors.joining());
            System.out.println(fromClient);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerMini server = new ServerMini();
        server.waitConnectionAndGet();
        ClientMini client = new ClientMini();
        client.sendToServer("Message to server from client");
        System.out.println("end");
    }
}