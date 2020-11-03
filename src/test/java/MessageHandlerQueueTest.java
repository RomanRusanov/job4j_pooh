import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The class test behavior MessageHandlerQueue.java class.
 */
class MessageHandlerQueueTest {
    /**
     * The String json.
     */
    private String json;
    /**
     * The instance parser.
     */
    private JSONParser jsonParser;
    /**
     * The tested instance.
     */
    private MessageHandlerQueue queue;
    /**
     * Message instance. Create parser.
     */
    private  Message message;

    /**
     * The method run before each test.
     */
    @BeforeEach
    void setUp() {
        this.json = "{\n"
                + "  \"queue\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"messageType\" : \"post\"\n"
                + "}";
        try {
            jsonParser = new JSONParser();
            this.message = jsonParser.handleJSON(this.json);
            queue = new MessageHandlerQueue();
            queue.post(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * If post success when collection contain 1 record.
     */
    @Test
    void post() {
        assertEquals(1, queue.getSize());
    }

    /**
     * If message exist get it in json representation.
     */
    @Test
    void get() {
        assertEquals("{\n"
                + "  \"queue\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\"\n"
                + "}",
                queue.get(message).getJSON());
    }
}