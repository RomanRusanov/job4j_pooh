import org.junit.jupiter.api.*;
import ver1.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class JSONParserTest {

    private JSONParser jsonParser;
    @BeforeEach
    void setUp() {


    }

    @Test
    void whenJSONComeQueueThenReturnObjectMessageQueueWithJSON() {
        String json = "{\n"
                + "  \"queue\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"messageType\" : \"post\"\n"
                + "}";
        jsonParser = new JSONParser();
        try {
            Message msg = jsonParser.handleJSON(json);
            assertEquals(json, msg.getJSON());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenJSONComeQueueThenReturnObjectMessageQueue() {
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

    @Test
    void whenJSONComeTopicThenReturnObjectMessageTopic() {
        String json = "{\n"
                + "  \"topic\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"ID\" : \"12345\",\n"
                + "  \"messageType\" : \"post\"\n"
                + "}";
        jsonParser = new JSONParser();
        try {
            Message msg = jsonParser.handleJSON(json);
            assertEquals("topic", msg.getTypeSequence());
            assertEquals("weather", msg.getTitle());
            assertEquals("temperature +18 C", msg.getText());
            assertEquals("12345", msg.getID());
            assertEquals("post", msg.getMessageType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void whenJSONComeTopicThenReturnObjectMessageTopicWithJSON() {
        String json = "{\n"
                + "  \"topic\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"messageType\" : \"post\"\n"
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