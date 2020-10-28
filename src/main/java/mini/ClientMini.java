package mini;

import java.io.*;
import java.net.*;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 28.10.2020
 * email roman9628@gmail.com
 * The class .
 */
public class ClientMini {

    public void sendToServer(String message) {
        try (
                Socket socket = new Socket(InetAddress.getLoopbackAddress(), 9000);
                PrintWriter out = new PrintWriter(
                     new BufferedOutputStream(socket.getOutputStream()), true)
        ){
            out.print(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}