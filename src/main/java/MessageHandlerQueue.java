import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageHandlerQueue implements MessageHandler{

    private final ConcurrentHashMap<
            String, ConcurrentLinkedQueue<Message>>
            allQueues = new ConcurrentHashMap<>();

    @Override
    public void post(Message message) {
        String queueName = message.getTitle();
        if (!allQueues.containsKey(queueName)) {
            allQueues.put(queueName, new ConcurrentLinkedQueue<>());
        }
        allQueues.get(queueName).add(message);
    }

    @Override
    public Message get(Message message) {
        String queueName = message.getTitle();
        return allQueues.get(queueName).peek();
    }

    public int getSize() {
        return allQueues.size();
    }
}
