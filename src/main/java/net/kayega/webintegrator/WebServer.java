package net.kayega.webintegrator;

import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;

public class WebServer implements Runnable {
    ServerSocket server = null;

    int port;
    String password = null;

    public WebServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            server.setReuseAddress(true);
            Bukkit.getLogger().log(Level.INFO, "Server started on *:1234");

            // running infinite loop for getting
            // client request
            while (!server.isClosed()) {

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                Bukkit.getLogger().log(Level.INFO, "New client connected "
                        + client.getInetAddress()
                        .getHostAddress());

                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client);

                if (password == null) {
                    new Thread(clientSock).start();
                    return;
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(WebIntegrator.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        if (clientSock.getState() != ClientHandler.ClientState.WaitingPassword)
                            return;
                        if (!client.isConnected())
                            return;
                        try {
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 10L * 20L);

                String line;
                BufferedReader in = client.isConnected() ? new BufferedReader(
                        new InputStreamReader(
                                client.getInputStream())) : null;
                PrintWriter out = client.isConnected() ? new PrintWriter(client.getOutputStream(), true) : null;
                while (in != null && (line = client.isConnected() ? in.readLine() : null) != null) {
                    if (Objects.requireNonNull(clientSock.getState()) == ClientHandler.ClientState.WaitingPassword) {
                        try {
                            Bukkit.getLogger().log(Level.INFO, line);
                            JSONObject json = new JSONObject(line);
                            if (json.getString("password").equals(this.password)) {
                                clientSock.setState(ClientHandler.ClientState.ClientAuthenticated);
                                if (out != null) {
                                    JSONObject connected = new JSONObject();
                                    connected.put("message", "Connected");
                                    out.println(connected.toString());
                                    out.flush();
                                }
                                new Thread(clientSock).start();
                            } else {
                                Bukkit.getLogger().log(Level.INFO, "Password invalid!");
                                client.close();
                            }
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                            client.close();
                            break;
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void close() {
        try {
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerSocket getServer() {
        return this.server;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
