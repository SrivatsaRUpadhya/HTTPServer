import java.util.ArrayList;
import java.util.Date;

public class HTTPResponse {
    StringBuilder ResponseBuilder;
    String res;
    ArrayList<String>Headers;

    public HTTPResponse(String _res){
        ResponseBuilder = new StringBuilder();
        String date = new Date().toString();
        Headers = new ArrayList<>();
        Headers.add(date);
        ResponseBuilder.append(_res).append('\n');
        for(String header : Headers)
            ResponseBuilder.append(header).append('\n');
    }

    public String getRes(){
        return ResponseBuilder.toString();
    }
}
