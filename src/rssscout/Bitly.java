package rssscout;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.json.JSONObject;

/**
 *
 * @author mike
 */
public class Bitly {
    private static final String APIUSER = MyBot.getBitlyUserName();
    private static final String APIKEY = MyBot.getBitlyApiKey();

    /**
     * Returns shortened url in a string
     *
     * @param query The url that needs shortening.
     * @return A shortened url string
     */
    public static String shortenURL(String query) {

        String finalString = "";

        try {
            // Convert spaces to +, etc. to make a valid URL
           String queryEncoded = URLEncoder.encode(query, "UTF-8");
            URL webUrl = new URL("http://api.bit.ly/shorten?version=2.0.1&longUrl=" + queryEncoded + "&login=" + APIUSER + "&apiKey=" + APIKEY);
            URLConnection connection = webUrl.openConnection();

            // Get the JSON response
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            String response = builder.toString();
            JSONObject json = new JSONObject(response);

            finalString = json.getJSONObject("results").getJSONObject(query).getString("shortUrl");
        } catch (Exception e) {
            System.err.println("Something went wrong...");
            e.printStackTrace();
        }
        return finalString;
    }

    public static String expandURL(String query) {

        String finalString = "";

        if(query.trim().startsWith("http://")){
            query = query.substring(7);
        }
//        System.out.println("without http: " + query);

        try {
            // Convert spaces to +, etc. to make a valid URL
           String queryEncoded = URLEncoder.encode(query, "UTF-8");
            URL webUrl = new URL("http://api.bit.ly/expand?version=2.0.1&shortUrl=" + queryEncoded + "&login=" + APIUSER + "&apiKey=" + APIKEY);
            String shortUrlHash = query.substring(7);
//            System.out.println("just hash: " + shortUrlHash);
            URLConnection connection = webUrl.openConnection();

            // Get the JSON response
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            String response = builder.toString();
            JSONObject json = new JSONObject(response);

            finalString = json.getJSONObject("results").getJSONObject(shortUrlHash).getString("longUrl");
        } catch (Exception e) {
            System.err.println("Something went wrong...");
            e.printStackTrace();
        }
        return finalString;
    }


}
