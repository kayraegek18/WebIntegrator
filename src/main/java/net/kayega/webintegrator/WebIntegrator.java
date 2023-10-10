package net.kayega.webintegrator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Level;

public final class WebIntegrator extends JavaPlugin implements Listener {

    private static WebIntegrator instance;

    public HashMap<Integer, WebPacket> packets = new HashMap<>();
    public List<WebServer> servers = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        //createServer(1234, "test");

        /*registerPacket(new WebPacket(0, new WebPacket.WebPacketCallback() {
            @Override
            public String run(JSONObject jsonData) {
                List<WebPlayer> players = new ArrayList<>();
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    UUID uuid = player.getUniqueId();
                    String name = player.getName();

                    double health = player.getHealth();
                    int food = player.getFoodLevel();
                    double xp = player.getExp();
                    int level = player.getLevel();

                    WebPlayer webPlayer = new WebPlayer();
                    webPlayer.setUuid(uuid.toString());
                    webPlayer.setName(name);

                    WebPlayerData webPlayerData = new WebPlayerData();
                    webPlayerData.setHealth(health);
                    webPlayerData.setFood(food);
                    webPlayerData.setXp(xp);
                    webPlayerData.setLevel(level);

                    webPlayer.setData(webPlayerData);

                    if (!players.contains(webPlayer))
                        players.add(webPlayer);
                }
                return new JSONArray(players).toString();
            }
        }));

        registerPacket(new WebPacket(1, new WebPacket.WebPacketCallback() {
            @Override
            public String run(JSONObject jsonData) {
                if (jsonData.getString("player").isEmpty()) {
                    JSONObject error = new JSONObject();
                    error.put("error", "Player not found!");
                    return error.toString();
                }

                WebPlayer webPlayer = null;
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (!player.getName().equalsIgnoreCase(jsonData.getString("player"))) {
                        try {
                            UUID jsonUUID = UUID.fromString(jsonData.getString("player"));
                            if (!player.getUniqueId().equals(jsonUUID)) {
                                continue;
                            }
                        }
                        catch (Exception e) {
                            continue;
                        }
                    }
                    UUID uuid = player.getUniqueId();
                    String name = player.getName();

                    double health = player.getHealth();
                    int food = player.getFoodLevel();
                    float xp = player.getExp();
                    int level = player.getLevel();

                    webPlayer = new WebPlayer();
                    webPlayer.setUuid(uuid.toString());
                    webPlayer.setName(name);

                    WebPlayerData webPlayerData = new WebPlayerData();
                    webPlayerData.setHealth(health);
                    webPlayerData.setFood(food);
                    webPlayerData.setXp(xp);
                    webPlayerData.setLevel(level);

                    webPlayer.setData(webPlayerData);
                }
                if (webPlayer == null) {
                    JSONObject error = new JSONObject();
                    error.put("error", "Player not found!");
                    return error.toString();
                }
                return new JSONObject(webPlayer).toString();
            }
        }));

        registerPacket(new WebPacket(2, new WebPacket.WebPacketCallback() {
            @Override
            public String run(JSONObject jsonData) {
                if (jsonData.getString("player").isEmpty()) {
                    JSONObject error = new JSONObject();
                    error.put("error", "Player not found!");
                    return error.toString();
                }

                JSONObject success = new JSONObject();
                success.put("message", "Successfully player health changed!");

                double newHealth = jsonData.getDouble("health");
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (!player.getName().equalsIgnoreCase(jsonData.getString("player"))) {
                        try {
                            UUID jsonUUID = UUID.fromString(jsonData.getString("player"));
                            if (!player.getUniqueId().equals(jsonUUID)) {
                                continue;
                            }
                        }
                        catch (Exception e) {
                            continue;
                        }
                    }
                    player.setHealth(newHealth);
                    return success.toString();
                }
                return success.toString();
            }
        }));

        registerPacket(new WebPacket(3, new WebPacket.WebPacketCallback() {
            @Override
            public String run(JSONObject jsonData) {
                if (jsonData.getString("player").isEmpty()) {
                    JSONObject error = new JSONObject();
                    error.put("error", "Player not found!");
                    return error.toString();
                }

                JSONObject success = new JSONObject();
                success.put("message", "Successfully player food changed!");

                int newFood = jsonData.getInt("food");
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (!player.getName().equalsIgnoreCase(jsonData.getString("player"))) {
                        try {
                            UUID jsonUUID = UUID.fromString(jsonData.getString("player"));
                            if (!player.getUniqueId().equals(jsonUUID)) {
                                continue;
                            }
                        }
                        catch (Exception e) {
                            continue;
                        }
                    }
                    player.setFoodLevel(newFood);
                    return success.toString();
                }
                return success.toString();
            }
        }));

        registerPacket(new WebPacket(4, new WebPacket.WebPacketCallback() {
            @Override
            public String run(JSONObject jsonData) {
                if (jsonData.getString("player").isEmpty()) {
                    JSONObject error = new JSONObject();
                    error.put("error", "Player not found!");
                    return error.toString();
                }

                JSONObject success = new JSONObject();
                success.put("message", "Successfully player xp changed!");

                float newExp = jsonData.getFloat("xp");
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (!player.getName().equalsIgnoreCase(jsonData.getString("player"))) {
                        try {
                            UUID jsonUUID = UUID.fromString(jsonData.getString("player"));
                            if (!player.getUniqueId().equals(jsonUUID)) {
                                continue;
                            }
                        }
                        catch (Exception e) {
                            continue;
                        }
                    }
                    player.setExp(newExp);
                    return success.toString();
                }
                return success.toString();
            }
        }));

        registerPacket(new WebPacket(5, new WebPacket.WebPacketCallback() {
            @Override
            public String run(JSONObject jsonData) {
                if (jsonData.getString("player").isEmpty()) {
                    JSONObject error = new JSONObject();
                    error.put("error", "Player not found!");
                    return error.toString();
                }

                JSONObject success = new JSONObject();
                success.put("message", "Successfully player level changed!");

                int newLevel = jsonData.getInt("level");
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (!player.getName().equalsIgnoreCase(jsonData.getString("player"))) {
                        try {
                            UUID jsonUUID = UUID.fromString(jsonData.getString("player"));
                            if (!player.getUniqueId().equals(jsonUUID)) {
                                continue;
                            }
                        }
                        catch (Exception e) {
                            continue;
                        }
                    }
                    player.setLevel(newLevel);
                    return success.toString();
                }
                return success.toString();
            }
        }));*/

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
