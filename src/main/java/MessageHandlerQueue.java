import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The class handle queue message.
 */
public class MessageHandlerQueue implements MessageHandler {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MessageHandlerQueue.class.getName());
    private final ConcurrentHashMap<
            String, ConcurrentLinkedQueue<Message>>
            allQueues = new ConcurrentHashMap<>();

    /**
     * The post message. Add message in common queue.
     * @param message Received message.
     */
    @Override
    public void post(Message message) {
        String queueName = message.getTitle();
        if (!allQueues.containsKey(queueName)) {
            allQueues.put(queueName, new ConcurrentLinkedQueue<>());
        }
        allQueues.get(queueName).add(message);
        LOG.info("POST/" + queueName);
    }

    /**
     * The get message. Find title of queue and get message from it.
     * @param message Received message.
     * @return Response message.
     */
    @Override
    public Message get(Message message) {
        String queueName = message.getTitle();
        return allQueues.get(queueName).peek();
    }

    /**
     * The method return collection size. Need for tests.
     * @return ins size.
     */
    public int getSize() {
        return allQueues.size();
    }
}
