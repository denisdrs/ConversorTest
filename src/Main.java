import java.io.BufferedReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
    public static void main(String[] args) throws Exception {
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/bcaf7dc4a8e07440ff214ec4/latest/USD";

        // Making Request
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Reading response
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(response.toString());
        JsonObject jsonobj = root.getAsJsonObject();

        // Accessing object
        String req_result = jsonobj.get("result").getAsString();

        // Print result
        System.out.println(req_result);

        // Print response body as JSON
        System.out.println("Response Body:");
        System.out.println(response.toString());
    }
}