package net.kayega.webintegrator;

import org.json.JSONObject;

public class WebPacket {
    private final int code;
    private final WebPacketCallback callback;

    public WebPacket(int code, WebPacketCallback callback) {
        this.code = code;
        this.callback = callback;
    }

    public int getCode() {
        return code;
    }

    public WebPacketCallback getCallback() {
        return callback;
    }

    public static interface WebPacketCallback {
        /**
         * Called when client sends with this code
         * @param jsonData Json data from client message
         * @return Server response to client
         */
        String run(JSONObject jsonData);
    }
}
