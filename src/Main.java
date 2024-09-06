import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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

                HTTPRequest req = null;
                ArrayList<String> inp = new ArrayList<>();
                String input;
                while ((input = in.readLine()) != null) {
                    inp.add(input);
                    if (input.isBlank()) {
                        req = new HTTPRequest(inp);
                        break;
                    }
                }

                // Generate and send response
                HTTPResponse res = new HTTPResponse();
                res.generateResponse(req);
                System.out.println(res.getRes());
                out.print(res.getRes());
                out.println("");
                out.flush();
                conn.close();
            }
        } catch (IOException e) {
            System.out.println("Io Exception");
        }
    }
}