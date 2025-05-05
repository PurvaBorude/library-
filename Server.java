import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        List<Socket> clients = new ArrayList<>();
        List<Long> clientTimes = new ArrayList<>();

        System.out.println("Server waiting for 2 clients...");

        // Accept 2 clients
        while (clients.size() < 2) {
            Socket client = server.accept();
            clients.add(client);
            System.out.println("Client connected: " + client.getInetAddress());
        }

        long serverTime = System.currentTimeMillis();
        System.out.println("Server time: " + serverTime);

        // Collect client times
        for (Socket client : clients) {
            DataInputStream in = new DataInputStream(client.getInputStream());
            long clientTime = in.readLong();
            clientTimes.add(clientTime);
        }

        // Compute average time difference
        long totalDiff = 0;
        for (Long time : clientTimes) {
            totalDiff += time - serverTime;
        }
        long avgDiff = totalDiff / (clientTimes.size() + 1); // +1 for server

        // Send adjustment to each client
        for (Socket client : clients) {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            long adjustment = avgDiff - (System.currentTimeMillis() - serverTime); // simulate passage of time
            out.writeLong(adjustment);
        }

        System.out.println("Time adjustment sent.");
        server.close();
    }
}
