import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void serverTest() {
        Server server = new Server(9000);
        server.getConnection();
        server.shutdown();
    }
}