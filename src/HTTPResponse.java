import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

interface ResponseStatusLines {
    public static final String ResOk = "HTTP/1.1 200 OK";
    public static final String ResNotFound = "HTTP/1.1 404 NotFound";
    public static final String ResNotImplemented = "HTTP/1.1 501 NotImplemented";
}

public class HTTPResponse {
    StringBuilder ResponseBuilder;
    ArrayList<String> Headers;

    public HTTPResponse() {
        ResponseBuilder = new StringBuilder();
        Headers = new ArrayList<>();
    }

    void AppendResponseHeaders() {
        String date = "Date: " + new Date().toString();
        String contentType = "Content-Type: text/html; charset=utf-8";
        String connection = "Connection: close";
        Headers.add(date);
        Headers.add(contentType);
        Headers.add(connection);
        for (String header : Headers)
            ResponseBuilder.append(header).append('\n');
    }

    void AppendStatusLine(String h) {
        ResponseBuilder.append(h).append('\n');
    }

    void AppendResponseBody(List<String> body) {
        ResponseBuilder.append(body);
    }

    void AppendResponseBody(String s) {
        ResponseBuilder.append("\n").append(s);
    }

    public String getRes() {
        return ResponseBuilder.toString();
    }

    public void generateResponse(HTTPRequest req) {
        switch (req.getMethod()) {
            case "GET":
                Path path = Paths.get(System.getProperty("user.dir") + "/assets" + req.getPath());
                System.out.println(path.toAbsolutePath());
                if (Files.exists(path.toAbsolutePath())) {
                    try {
                        AppendStatusLine(ResponseStatusLines.ResOk);
                        List<String> data = Files.readAllLines(path);
                        AppendResponseHeaders();
                        for (String line : data)
                            AppendResponseBody(line);
                    } catch (IOException e) {
                        System.out.println("Error generating response");
                        System.out.println(e.toString());
                        return;
                    }
                } else {
                    AppendStatusLine(ResponseStatusLines.ResNotFound);
                    String notFound = "<h1>404 Not Found</h1>";
                    AppendResponseHeaders();
                    AppendResponseBody(notFound);
                }
                break;
            default:
                AppendStatusLine(ResponseStatusLines.ResNotImplemented);
                AppendResponseHeaders();
                break;
        }
    }

}
