package pooh;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Roman Rusanov
 * @since 26.03.2021
 * email roman9628@gmail.com
 * ServerTest1 and ServerTest2 must run independently. On each test one server thread!!!
 */
public class ServerTest2 {

    /**
     * The test check topic. If queue post, when response "POST/OK".
     * For topic client must send in json id
     * by this id server create unique queue for this client.
     * if server receive get message with new id for existing topic title,
     * when server create new queue with all message that was accepted for
     * this topic title.
     */
    @Test
    public void secondTest() throws InterruptedException {
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
            Server server = new Server(50500);
            server.acceptConnections();
        }).start();

        Client client = new Client();
        System.out.println("---------------1st Post---------------------");
        assertEquals("POST/OK", client.sendToServer(jsonPostWeather1));
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
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"rainy +10 C\""
                + "}", client.sendToServer(jsonGetSub111));
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"rainy +10 C\""
                + "}", client.sendToServer(jsonGetSub222));
        assertEquals("{"
                + "  \"topic\" : \"weather\","
                + "  \"text\" : \"rainy +10 C\""
                + "}", client.sendToServer(jsonGetSub333));
    }

}