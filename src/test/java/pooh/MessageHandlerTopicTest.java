package pooh;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * The class test behavior pooh.MessageHandlerTopic.java
 */
public class MessageHandlerTopicTest {
    /**
     * The String json.
     */
    private String json;
    /**
     * The String json with other id.
     */
    private String jsonWithAnotherID;
    /**
     * The instance parser.
     */
    private JSONParser jsonParser;
    /**
     * The tested instance.
     */
    private MessageHandlerTopic queue;
    /**
     * pooh.Message instance. Create parser.
     */
    private  Message message;

    /**
     * The method run before each test.
     */
    @Before
    public void setUp() {
        this.json = "{\n"
                + "  \"topic\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"id\" : \"123\",\n"
                + "  \"messageType\" : \"post\"\n"
                + "}";

        this.jsonWithAnotherID = "{\n"
                + "  \"topic\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"id\" : \"111\",\n"
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


    /**
     * If change only id and topic equals in get message when return
     * new queue with all previous message.
     * if id equals when pool from one queue.
     */
    @Test
    public void get() {
        MessageTopic send = null;
        try {
            this.jsonParser = new JSONParser();
            send = (MessageTopic) jsonParser.handleJSON(jsonWithAnotherID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message receive1 = queue.get(send);
        assertEquals("topic", receive1.getTypeSequence());
        assertEquals("weather", receive1.getTitle());
        assertEquals("temperature +18 C", receive1.getText());

        send.setId("222");
        Message receive2 = queue.get(send);
        assertEquals("topic", receive2.getTypeSequence());
        assertEquals("weather", receive2.getTitle());
        assertEquals("temperature +18 C", receive2.getText());

        send.setText("temperature +25 C");
        queue.post(send);
        Message receive3 = queue.get(send);
        assertEquals("topic", receive3.getTypeSequence());
        assertEquals("weather", receive3.getTitle());
        assertEquals("temperature +25 C", receive3.getText());
        send.setId("111");
        Message receive4 = queue.get(send);
        assertEquals("topic", receive4.getTypeSequence());
        assertEquals("weather", receive4.getTitle());
        assertEquals("temperature +25 C", receive4.getText());
        send.setTopic("solar activity");
        queue.post(send);
        assertEquals(2, this.queue.getSize());
    }
}