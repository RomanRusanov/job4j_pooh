package pooh;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * The class test behavior pooh.JSONParser.java
 */
public class JSONParserTest {
    /**
     * The tested instance.
     */
    private JSONParser jsonParser;

    /**
     * Test.
     */
    @Test
    public void whenJSONComeQueueThenReturnObjectMessageQueueWithJSON() {
        String json = "{\n"
                + "  \"queue\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\"\n"
                + "}";
        jsonParser = new JSONParser();
        try {
            Message msg = jsonParser.handleJSON(json);
            assertEquals(json, msg.getJSON());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test.
     */
    @Test
    public void whenJSONComeQueueThenReturnObjectMessageQueue() {
        String json = "{\n"
                + "  \"queue\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"messageType\" : \"post\"\n"
                + "}";
        jsonParser = new JSONParser();
        try {
            Message msg = jsonParser.handleJSON(json);
            assertEquals("queue", msg.getTypeSequence());
            assertEquals("weather", msg.getTitle());
            assertEquals("temperature +18 C", msg.getText());
            assertEquals("post", msg.getMessageType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test.
     */
    @Test
    public void whenJSONComeTopicThenReturnObjectMessageTopic() {
        String json = "{\n"
                + "  \"topic\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"id\" : \"12345\",\n"
                + "  \"messageType\" : \"post\"\n"
                + "}";
        jsonParser = new JSONParser();
        try {
            Message msg = jsonParser.handleJSON(json);
            assertEquals("topic", msg.getTypeSequence());
            assertEquals("weather", msg.getTitle());
            assertEquals("temperature +18 C", msg.getText());
            assertEquals("post", msg.getMessageType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test.
     */
    @Test
    public void whenJSONComeTopicThenReturnObjectMessageTopicWithJSON() {
        String json = "{\n"
                + "  \"topic\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\"\n"
                + "}";
        jsonParser = new JSONParser();
        try {
            Message msg = jsonParser.handleJSON(json);
            assertEquals(json, msg.getJSON());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}