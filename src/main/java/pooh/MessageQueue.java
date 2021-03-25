package pooh;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 16.10.2020
 * email roman9628@gmail.com
 * The class describe type queue of message..
 */
public class MessageQueue implements Message {
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
                + "  \"text\" : \"%s\"\n"
                + "}", this.queue, this.text);
    }

    /**
     * The getter for field.
     * @return Text content.
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
        return queue;
    }

    /**
     * The ID for subscriber identification/
     *
     * @return String.
     */
    @Override
    public String getId() {
        return "queue not need ID";
    }
}