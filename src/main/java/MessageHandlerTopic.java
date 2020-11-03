import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageHandlerTopic implements MessageHandler {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MessageHandlerTopic.class.getName());
    /**
     * The collection contain all message that be post at queues. When add new subscriber,
     * then it takes all previous message.
     */
    private final static ConcurrentHashMap<
            String, ConcurrentLinkedQueue<Message>>
            ADD_ONLY = new ConcurrentHashMap<>();
    /**
     * The collection contain subscribers queues. Each subscriber get from individual queue.
     */
    private final static ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<Message>>> SUB = new ConcurrentHashMap<>();

    /**
     * The method add message to all queues.
     * @param message Received message.
     */
    @Override
    public void post(Message message) {
        String queueName = message.getTitle();
        if (!ADD_ONLY.containsKey(queueName)) {
            ADD_ONLY.put(queueName, new ConcurrentLinkedQueue<>());
        }
        ADD_ONLY.get(queueName).add(message);
        if (SUB.containsKey(queueName)) {
            SUB.get(queueName).forEach((id, queue) -> queue.add(message));
        }
    }

    /**
     * The method check topic present in outer collection if true
     * check (if id present in inner collection get it, otherwise
     * add new queue in inner collection) and poll it. Otherwise generate message
     * with text error - "Error! Message not receive yet."
     * @param message Received message.
     * @return Message response.
     */
    @Override
    public Message get(Message message) {
        Message result;
        String queueName = message.getTitle();
        if (!ADD_ONLY.containsKey(queueName)) {
            LOG.warn("Message not receive yet: " + message.getTitle());
            MessageTopic msg = new MessageTopic();
            msg.setTopic(message.getTitle());
            msg.setText("Error! Message not receive yet.");
            result = msg;
        } else {
            if (!SUB.containsKey(queueName)) {
                SUB.put(queueName, new ConcurrentHashMap<>());
                SUB.get(queueName)
                        .put(message.getId(), new ConcurrentLinkedQueue<>(ADD_ONLY.get(queueName)));
            } else {
                if (!SUB.get(queueName).containsKey(message.getId())) {
                    SUB.get(queueName)
                            .put(message.getId(), new ConcurrentLinkedQueue<>(ADD_ONLY.get(queueName)));
                }
            }
            result = SUB.get(queueName).get(message.getId()).poll();
        }
        return result;
    }
    /**
     * The method return collection size. Need for tests.
     * @return ins size.
     */
    public int getSize() {
        return ADD_ONLY.size();
    }
}
