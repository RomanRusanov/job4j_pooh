import org.junit.jupiter.api.*;
import java.net.*;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {


    private final String jsonPost = "{\n"
            + "  \"queue\" : \"weather\",\n"
            + "  \"text\" : \"temperature +18 C\",\n"
            + "  \"ID\" : \"11111\",\n"
            + "  \"messageType\" : \"post\"\n"
            + "}";

    private final String jsonGet = "{\n"
            + "  \"queue\" : \"weather\",\n"
            + "  \"text\" : \"temperature +18 C\",\n"
            + "  \"ID\" : \"11111\",\n"
            + "  \"messageType\" : \"get\"\n"
            + "}";


    @BeforeEach
    void setUp() {
    }

    @Test
    void getMessageAndReply() {
        Server server = new Server();
        int portPublisher = server.getPort();
        InetAddress address = server.getAddress();
        Publisher publisher = new Publisher();
        publisher.requestToServer(address, portPublisher, jsonPost);

        Subscriber subscriber = new Subscriber();
        int portSubscriber = server.getPort();
        subscriber.requestToServer(address, portSubscriber, jsonGet);
        server.close();
    }
}