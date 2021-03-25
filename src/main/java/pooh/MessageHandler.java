package pooh;

public interface MessageHandler {

    void post(Message message);

    Message get(Message message);
}
