import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int PORT = 42069;
        try{
            ServerSocket s = new ServerSocket(PORT);
            while(true){
                System.out.println("Waiting for connection..");
                Socket conn = s.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                PrintWriter out = new PrintWriter(conn.getOutputStream());

                // Read input from the client and send it back as response
                String input;
                while((input = in.readLine()) != null){
                    out.println(input);
                    out.flush();
                    System.out.println("Received:" + input);
                }
            }
        }
        catch(IOException e){
            System.out.println("Io Exception");

        }

    }
}