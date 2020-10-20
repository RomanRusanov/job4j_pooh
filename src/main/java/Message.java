/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 16.10.2020
 * email roman9628@gmail.com
 * The interface describe message.
 */
public interface Message {
    /**
     * The message type.
     * @return String.
     */
    String getTypeSequence();

    /**
     * The method return title type.
     * @return String.
     */
    String getTitle();

    /**
     * The String json representation of message.
     * @return json string.
     */
    String getJSON();

    /**
     * The getter for field.
     * @return String (post, get or other)
     */
    String getMessageType();

    /**
     * The getter for field.
     * @return String text.
     */
    String getText();

    /**
     * The ID for subscriber identification/
     * @return String.
     */
    String getID();
}
