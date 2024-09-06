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

                HTTPRequest req;
                ArrayList<String> inp = new ArrayList<>();
                String input;
                while ((input = in.readLine()) != null) {
                    inp.add(input);
                    if(input.isBlank()) {
                        req = new HTTPRequest(inp);
                        break;
                    }
                }

                // Generate and send response
                String ResOk = "HTTP/1.1 200 Ok";
                HTTPResponse res = new HTTPResponse(ResOk);
                out.println(res.getRes());
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("Io Exception");
        }

    }
}