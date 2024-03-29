package pooh;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * The class test behavior pooh.MessageHandlerQueue.java class.
 */
public class MessageHandlerQueueTest {
    /**
     * The tested instance.
     */
    private MessageHandlerQueue queue;
    /**
     * pooh.Message instance. Create parser.
     */
    private Message message;

    /**
     * The method run before each test.
     */
    @Before
    public void setUp() {
        /**
         * The String json.
         */
        String json = "{\n"
                + "  \"queue\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\",\n"
                + "  \"messageType\" : \"post\"\n"
                + "}";
        try {
            /**
             * The instance parser.
             */
            JSONParser jsonParser = new JSONParser();
            this.message = jsonParser.handleJSON(json);
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
    public void post() {
        assertEquals(1, queue.getSize());
    }

    /**
     * If message exist get it in json representation.
     */
    @Test
    public void get() {
        assertEquals("{\n"
                + "  \"queue\" : \"weather\",\n"
                + "  \"text\" : \"temperature +18 C\"\n"
                + "}",
                queue.get(message).getJSON());
    }
}