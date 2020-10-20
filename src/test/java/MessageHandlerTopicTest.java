import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class MessageHandlerTopicTest {
    private String json;

    private JSONParser jsonParser;

    private MessageHandlerTopic queue;

    private  Message message;

    @BeforeEach
    void setUp() {
        this.json = "{\n"
                + "  \"topic\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"ID\" : \"12345\",\n"
                + "  \"messageType\" : \"post\"\n"
                + "}";
        try {
            jsonParser = new JSONParser();
            this.message = jsonParser.handleJSON(json);
            queue = new MessageHandlerTopic();
            queue.post(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    /**
     * If change only id and topic equals in get message when return
     * new queue with all previous message.
     * if id equals when pool from one queue.
     */
    void get() {
        String jsonWithAnotherID = "{\n"
                + "  \"topic\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"ID\" : \"11111\",\n"
                + "  \"messageType\" : \"post\"\n"
                + "}";
        try {
            this.jsonParser = new JSONParser();
            MessageTopic send = (MessageTopic) jsonParser.handleJSON(jsonWithAnotherID);

            Message receive1 = queue.get(send);
            assertEquals("topic", receive1.getTypeSequence());
            assertEquals("weather", receive1.getTitle());
            assertEquals("temperature +18 C", receive1.getText());
            assertEquals("12345", receive1.getID());
            send.setID("22222");
            Message receive2 = queue.get(send);
            assertEquals("topic", receive2.getTypeSequence());
            assertEquals("weather", receive2.getTitle());
            assertEquals("temperature +18 C", receive2.getText());
            assertEquals("12345", receive2.getID());

            send.setText("temperature +25 C");
            queue.post(send);
            Message receive3 = queue.get(send);
            assertEquals("topic", receive3.getTypeSequence());
            assertEquals("weather", receive3.getTitle());
            assertEquals("temperature +25 C", receive3.getText());
            assertEquals("22222", receive3.getID());
            send.setID("11111");
            Message receive4 = queue.get(send);
            assertEquals("topic", receive4.getTypeSequence());
            assertEquals("weather", receive4.getTitle());
            assertEquals("temperature +25 C", receive4.getText());
            assertEquals("11111", receive4.getID());
            send.setTopic("solar activity");
            queue.post(send);
            assertEquals(2, this.queue.getSize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}