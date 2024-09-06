import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        int PORT = 42069;
        try {
            ServerSocket s = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for connection..");
                Socket conn = s.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                PrintWriter out = new PrintWriter(conn.getOutputStream());

                // Read input from the client and send it back as response
                String input;

                String[] ReqComponents = in.readLine().split(" ");
                String method = ReqComponents[0];
                String path = ReqComponents[1];
                String ver = ReqComponents[2];

                ArrayList<String> ReqHeaders = new ArrayList<>();
                while ((input = in.readLine()) != null) {
                    ReqHeaders.add(input);
                    if(input.isBlank()) break;
                }

                // Generate and send response
                String ResOk = "HTTP/1.1 200 Ok";
                ArrayList<String> ResHeaders = new ArrayList<>();
                String date = new Date().toString();

                ResHeaders.add(date);
                sb.append(ResOk).append('\n');
                for(String h : ResHeaders)
                    sb.append(h).append('\n');

                out.println(sb.toString());
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("Io Exception");
        }

    }
}