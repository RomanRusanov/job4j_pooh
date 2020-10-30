package ver1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 16.10.2020
 * email roman9628@gmail.com
 * The class .
 */
public class MessageTopic implements Message{
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MessageTopic.class.getName());
    /**
     * The field contain sequence type.
     */
    private final String typeSequence = "topic";
    /**
     * The field contain title of topic.
     */
    private String topic;
    /**
     * The field contain text.
     */
    private String text;
    /**
     * The ID subscriber.
     */
    private String ID;
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
                + "  \"topic\" : \"%s\",\n"
                + "  \"text\" : \"%s\",\n"
                + "  \"ID\" : \"%s\",\n"
                + "  \"messageType\" : \"%s\"\n"
                + "}", this.topic, this.text, this.ID, this.messageType);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
        return topic;
    }

    /**
     * The ID for subscriber identification/
     *
     * @return String.
     */
    @Override
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}