import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        long localTime = System.currentTimeMillis();
        out.writeLong(localTime);
        System.out.println("Sent local time: " + localTime);

        long adjustment = in.readLong();
        long newTime = localTime + adjustment;
        System.out.println("Adjustment received: " + adjustment);
        System.out.println("Adjusted time: " + newTime);

        socket.close();
    }
}
