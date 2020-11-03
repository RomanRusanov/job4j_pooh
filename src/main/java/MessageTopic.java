/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 16.10.2020
 * email roman9628@gmail.com
 * The class .
 */
public class MessageTopic implements Message {
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
    private String id;
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
                + "  \"text\" : \"%s\"\n"
                + "}", this.topic, this.text);
    }

    /**
     * The setter for field.
     * @param topic String.
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * The getter for field.
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * The setter for field.
     * @param text String.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * The getter for field.
     * @return String.
     */
    public String getMessageType() {
        return messageType;
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
    public String getId() {
        return id;
    }

    /**
     * The setter for field.
     * @param id String.
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    /**
     * Method override to string
     * @return String representation.
     */
    @Override
    public String toString() {
        return "MessageTopic{"
                + "typeSequence='" + typeSequence + '\''
                + ", topic='" + topic + '\''
                + ", text='" + text + '\''
                + ", id='" + id + '\''
                + ", messageType='" + messageType + '\''
                + '}';
    }
}