package pooh;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * The class test behavior Server.java
 */
public class ServerTest {
    /**
     * The test queue. If queue post, when response "POST/OK".
     * If queue get when response json with text content.
     */
    @Test
    public void queueTest() {
        Server server = new Server(60000);
        new Thread(server::acceptConnections).start();
        String jsonPost = "{"
                + "  \"queue\" : \"weather\","
                + "  \"text\" : \"temperature +18 C\","
                + "  \"messageType\" : \"post\""
                + "}";

        String jsonGet = "{"
                + "  \"queue\" : \"weather\","
                + "  \"messageType\" : \"get\""
                + "}";
        Client client = new Client(60000);
        assertEquals("POST/OK", client.sendToServer(jsonPost));
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
        Server server = new Server(50000);
        new Thread(server::acceptConnections).start();
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
        Client client = new Client(50000);
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