import java.util.ArrayList;

public class HTTPRequest {
    ArrayList<String>Headers;
    String method;
    String path;
    String ver;

    public HTTPRequest(ArrayList<String> req){
        String[] r = req.getFirst().split(" ");
        method = r[0];
        path = r[1];
        ver = r[2];

        Headers = new ArrayList<>();
        for(int i = 1; i < req.size();i++)
            Headers.add(req.get(i));
    }
}
