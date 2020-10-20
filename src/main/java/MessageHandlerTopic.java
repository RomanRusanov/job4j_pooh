import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class MessageHandlerTopic implements MessageHandler{
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(JSONParser.class.getName());

    private final static ConcurrentHashMap<
            String, ConcurrentLinkedQueue<Message>>
            queuesAddOnly = new ConcurrentHashMap<>();

    private final static ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<Message>>> queuesSubscribers = new ConcurrentHashMap<>();

    @Override
    public void post(Message message) {
        String queueName = message.getTitle();
        if (!queuesAddOnly.containsKey(queueName)) {
            queuesAddOnly.put(queueName, new ConcurrentLinkedQueue<>());
        }
        queuesAddOnly.get(queueName).add(message);
        if (queuesSubscribers.containsKey(queueName)) {
            queuesSubscribers.get(queueName).forEach((id, queue) -> queue.add(message));
        }
    }

    @Override
    public Message get(Message message) {
        Message result = null;
        String queueName = message.getTitle();
        if (!queuesAddOnly.containsKey(queueName)) {
            LOG.warn(String.format("Message:(%s) not receive yet."), message.getTitle());
            result = message;
        } else {
            if (!queuesSubscribers.containsKey(queueName)) {
                queuesSubscribers.put(queueName, new ConcurrentHashMap<>());
                queuesSubscribers.get(queueName)
                        .put(message.getID(), new ConcurrentLinkedQueue<>(queuesAddOnly.get(queueName)));
                result = queuesSubscribers.get(queueName).get(message.getID()).poll();
            } else {
                if (!queuesSubscribers.get(queueName).containsKey(message.getID())) {
                    queuesSubscribers.get(queueName)
                            .put(message.getID(), new ConcurrentLinkedQueue<>(queuesAddOnly.get(queueName)));
                    result = queuesSubscribers.get(queueName).get(message.getID()).poll();
                } else {
                    result = queuesSubscribers.get(queueName).get(message.getID()).poll();
                }
            }
        }
        return result;
    }

    public int getSize(){
        return queuesAddOnly.size();
    }
}
