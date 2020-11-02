import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void serverTest() {
        new Thread(() ->{
            Server server = new Server(9000);
            server.getConnection();
        }).start();


        Client client = new Client();
        client.sendToServer("hello   ");
    }
}