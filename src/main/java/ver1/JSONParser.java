package ver1;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 16.10.2020
 * email roman9628@gmail.com
 * The class .
 */
public class JSONParser {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(JSONParser.class.getName());

    private final Map<String, Message> messages = new HashMap();

    public JSONParser() {
        this.fillMessages();
    }

    private void fillMessages() {
        messages.put("queue", new MessageQueue());
        messages.put("topic", new MessageTopic());
    }
    /**
     * Handle an Object. Consume the first token which is BEGIN_OBJECT. Within
     * the Object there could be array or non array tokens. We write handler
     * methods for both. Noe the peek() method. It is used to find out the type
     * of the next token without actually consuming it.
     *
     * @throws IOException
     */
    public Message handleJSON(String json) throws IOException {
        Message result = null;
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.beginObject();
        JsonToken token = reader.peek();
        if (token.equals(JsonToken.NAME)) {
            String type = reader.nextName();
            if (!messages.containsKey(type)) {
                LOG.error(String.format("This json message type:%s not exist.", type));
            } else {
                Message msg = messages.get(type);
                Gson gson = new Gson();
                result = gson.fromJson(json, msg.getClass());
            }
        }
        return result;
    }
}