import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 16.10.2020
 * email roman9628@gmail.com
 * The class describe type queue of message..
 */
public class MessageQueue implements Message{
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MessageQueue.class.getName());
    /**
     * The field contain sequence type.
     */
    private final String typeSequence = "queue";
    /**
     * The field contain title of queue.
     */
    public String queue;
    /**
     * The field contain text.
     */
    public String text;
    /**
     * The field contain message type.
     * (Post, Get)
     */
    private String messageType;

    /**
     * The getter for method.
     * @return String type.
     */
    @Override
    public String getTypeSequence() {
        return this.typeSequence;
    }

    /**
     * The method return JSON string for this object.
     * @return String json.
     */
    @Override
    public String getJSON() {
        return String.format("{\n"
                + "  \"queue\" : \"%s\",\n"
                + "  \"text\" : \"%s\",\n"
                + "  \"messageType\" : \"%s\"\n"
                + "}", this.queue, this.text, this.messageType);
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * The method return title type.
     *
     * @return String.
     */
    @Override
    public String getTitle() {
        return queue;
    }

    /**
     * The ID for subscriber identification/
     *
     * @return String.
     */
    @Override
    public String getID() {
        return "queue not need ID";
    }
}