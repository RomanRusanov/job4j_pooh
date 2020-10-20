import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class MessageHandlerQueueTest {

    private String json;

    private JSONParser jsonParser;

    private MessageHandlerQueue queue;

    private  Message message;

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

    @Test
    void post() {
        assertEquals(1, queue.getSize());
    }

    @Test
    void get() {
        assertEquals(this.json, queue.get(message).getJSON());
    }
}