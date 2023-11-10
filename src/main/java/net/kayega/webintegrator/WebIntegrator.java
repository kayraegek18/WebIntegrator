package net.kayega.webintegrator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Level;

public class WebIntegrator extends JavaPlugin implements Listener {

    private static WebIntegrator instance;

    public HashMap<Integer, WebPacket> packets = new HashMap<>();
    public List<WebServer> servers = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        for (WebServer server : servers) {
            server.close();
        }
    }

    public static WebIntegrator getInstance() {
        return instance;
    }

    public void registerPacket(WebPacket packet) {
        packets.put(packet.getCode(), packet);
    }

    public CreateServerReturnParams createServer(int port) {
        WebServer server = new WebServer(port);
        Thread serverThread = new Thread(server);
        servers.add(server);
        serverThread.start();
        return new CreateServerReturnParams(serverThread, server);
    }

    public CreateServerReturnParams createServer(int port, String password) {
        WebServer server = new WebServer(port);
        server.setPassword(password);
        Thread serverThread = new Thread(server);
        servers.add(server);
        serverThread.start();
        return new CreateServerReturnParams(serverThread, server);
    }

    public static class CreateServerReturnParams {
        Thread serverThread;
        WebServer serverRunnable;

        public CreateServerReturnParams(Thread serverThread, WebServer serverRunnable) {
            this.serverThread = serverThread;
            this.serverRunnable = serverRunnable;
        }

        public Thread getServerThread() {
            return serverThread;
        }

        public WebServer getServerRunnable() {
            return serverRunnable;
        }
    }
}
