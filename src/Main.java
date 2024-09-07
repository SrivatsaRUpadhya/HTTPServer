import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class RequestHandler implements Runnable {
    Socket con;

    public RequestHandler(Socket _con) {
        con = _con;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            PrintWriter out = new PrintWriter(con.getOutputStream());

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
            con.close();
        } catch (IOException e) {
            System.out.println("IO Exception when reading request");
            System.out.println(e.toString());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        int PORT = 42069;
        try {
            ExecutorService e = Executors.newFixedThreadPool(10);
            ServerSocket s = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for connection..");
                Socket con = s.accept();
                e.execute(new RequestHandler(con));
            }
        } catch (IOException e) {
            System.out.println("Io Exception");
        }
    }
}