package net.kayega.webintegrator;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    // Constructor
    public ClientHandler(Socket socket)
    {
        this.clientSocket = socket;
    }

    public void run()
    {
        PrintWriter out = null;
        BufferedReader in = null;
        try {

            // get the outputstream of client
            out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            // get the inputstream of client
            in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                Bukkit.getLogger().log(Level.INFO,
                        "Sent from the client: " + line);

                JSONObject message = new JSONObject(line);

                for (Integer integer : WebIntegrator.getInstance().packets.keySet()){
                    if (message.getInt("code") == integer) {
                        String response = WebIntegrator.getInstance().packets.get(integer).getCallback().run(
                                new JSONObject(message.getString("data"))
                        );

                        JSONObject jo = new JSONObject();
                        jo.put("code", integer);
                        jo.put("response", response);

                        out.println(jo.toString());
                        out.flush();
                    }
                }
            }
        }
        catch (IOException e) {

        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
