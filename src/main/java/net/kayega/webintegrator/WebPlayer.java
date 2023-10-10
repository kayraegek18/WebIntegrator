package net.kayega.webintegrator;

import java.io.Serializable;
import java.util.UUID;

public class WebPlayer implements Serializable {
    private String uuid;
    private String name;
    private WebPlayerData data;

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public WebPlayerData getData() {
        return data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setData(WebPlayerData data) {
        this.data = data;
    }
}