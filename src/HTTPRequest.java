import java.util.ArrayList;

public class HTTPRequest {
    ArrayList<String> Headers;
    String method;
    String path;
    String ver;
    String body;

    public HTTPRequest(ArrayList<String> req) {
        String[] r = req.getFirst().split(" ");
        method = r[0];
        path = r[1];
        ver = r[2];

        if (method.equals("POST")) {
            body = path.substring(path.indexOf("?") + 1);
        }
        Headers = new ArrayList<>();
        for (int i = 1; i < req.size(); i++)
            Headers.add(req.get(i));
    }

    public ArrayList<String> getHeaders() {
        return Headers;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVer() {
        return ver;
    }

    public ArrayList<String[]> getQueryParams() {
        ArrayList<String[]> queryParams = new ArrayList<>();
        if (body.isBlank()) return queryParams;
        String[] paramsList = body.split("&");
        for (String param : paramsList) {
            queryParams.add(param.split("="));
        }
        return queryParams;
    }
}
