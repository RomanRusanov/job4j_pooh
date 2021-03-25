package pooh;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The class test behavior pooh.Server.java
 */
class ServerTest {

    /**
     * The test queue. If queue post, when response "POST/OK".
     * If queue get when response json with text content.
     */
    @Test
    void queueTest() {
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

    /**
     * The test check topic. If queue post, when response "POST/OK".
     * For topic client must send in json id
     * by this id server create unique queue for this client.
     * if server receive get message with new id for existing topic title,
     * when server create new queue with all message that was accepted for
     * this topic title.
     */
    @Test
    void topicTest() throws InterruptedException {
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

        String jsonGetSub333 = "{"
                + "  \"topic\" : \"weather\","
                + "  \"id\" : \"333\","
                + "  \"messageType\" : \"get\""
                + "}";


        new Thread(() -> {
            Server server = new Server(9000);
            server.acceptConnections();
        }).start();

        Client client = new Client();
        assertEquals("POST/OK", client.sendToServer(jsonPostWeather1));
        System.out.println("---------------1st Post---------------------");
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"temperature +25 C\""
                + "}", client.sendToServer(jsonGetSub111));
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"temperature +25 C\""
                + "}", client.sendToServer(jsonGetSub222));
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"temperature +25 C\""
                + "}", client.sendToServer(jsonGetSub333));
        System.out.println("---------------2nd Post---------------------");
        assertEquals("POST/OK", client.sendToServer(jsonPostWeather2));
        Thread.sleep(500);
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"rainy +10 C\""
                + "}", client.sendToServer(jsonGetSub111));
        Thread.sleep(500);
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"rainy +10 C\""
                + "}", client.sendToServer(jsonGetSub222));
        Thread.sleep(500);
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"rainy +10 C\""
                + "}", client.sendToServer(jsonGetSub333));
    }
}