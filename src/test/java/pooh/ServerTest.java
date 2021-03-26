package pooh;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The class test behavior Server.java
 * ServerTest1 and ServerTestTopic must run independently. On each test one server thread!!!
 */

public class ServerTest {

    /**
     * The test queue. If queue post, when response "POST/OK".
     * If queue get when response json with text content.
     */
    @Test
    public void queueTest() {
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
            Server server = new Server(50500);
            server.acceptConnections();
        });
        serverThread.start();
        Client client = new Client(50500);
        assertEquals("POST/OK", client.sendToServer(jsonPost));
        System.out.println("Queue test!==============");
        assertEquals("{"
                + "  \"queue\" : \"weather\","
                + "  \"text\" : \"temperature +18 C\""
                + "}", client.sendToServer(jsonGet));
    }

    /**
     * The test check topic. If queue post, when response "POST/OK".
     * For topic client must send in json id
     * by this id server create unique queue for this client.
     * if server receive get message with new id for existing topic title,
     * when server create new queue with all message that was accepted for
     * this topic title.
     */

    @Test
    public void topicTest() {
        String jsonPostWeather1 = "{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"temperature +25 C\","
                + "  \"messageType\" : \"post\""
                + "}";

        String jsonPostWeather2 = "{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"rainy +10 C\","
                + "  \"messageType\" : \"post\""
                + "}";

        String jsonGetSub111 = "{"
                + "  \"topic\" : \"weather\","
                + "  \"id\" : \"111\","
                + "  \"messageType\" : \"get\""
                + "}";

        String jsonGetSub222 = "{"
                + "  \"topic\" : \"weather\","
                + "  \"id\" : \"222\","
                + "  \"messageType\" : \"get\""
                + "}";
        Client client = new Client(50500);
        System.out.println("Topic test------------------------");
        assertEquals("POST/OK", client.sendToServer(jsonPostWeather1));
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"temperature +25 C\""
                + "}", client.sendToServer(jsonGetSub111));
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"temperature +25 C\""
                + "}", client.sendToServer(jsonGetSub222));
        assertEquals("POST/OK", client.sendToServer(jsonPostWeather2));
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"rainy +10 C\""
                + "}", client.sendToServer(jsonGetSub111));
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"rainy +10 C\""
                + "}", client.sendToServer(jsonGetSub222));
    }
}