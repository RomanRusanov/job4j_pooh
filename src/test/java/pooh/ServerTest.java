package pooh;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The class test behavior Server.java
 * ServerTest1 and ServerTest2 must run independently. On each test one server thread!!!
 */
public class ServerTest {

    /**
     * The test queue. If queue post, when response "POST/OK".
     * If queue get when response json with text content.
     */
    @Test
    public void firstTest() {
        String jsonPost = "{"
                + "  \"queue\" : \"weather\","
                + "  \"text\" : \"temperature +18 C\","
                + "  \"messageType\" : \"post\""
                + "}";

        String jsonGet = "{"
                + "  \"queue\" : \"weather\","
                + "  \"messageType\" : \"get\""
                + "}";
        Thread serverThread = new Thread(() -> {
            Server server = new Server(9000);
            server.acceptConnections();
        });
        serverThread.start();
        Client client = new Client();
        assertEquals("POST/OK", client.sendToServer(jsonPost));
        System.out.println("---------------1st Post---------------------");
        assertEquals("{"
                + "  \"queue\" : \"weather\","
                + "  \"text\" : \"temperature +18 C\""
                + "}", client.sendToServer(jsonGet));
    }
}