package apifetech;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiFetcher {

    public static void main(String[] args) {
        String apiUrl = "https://jsonplaceholder.typicode.com/posts"; // API URL for fetching posts
        try {
            // Fetch data from API
            String response = fetchDataFromApi(apiUrl);
            System.out.println("Response from API:");
            System.out.println(response);

            // Parse the JSON response
            parseJsonResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String fetchDataFromApi(String apiUrl) throws Exception {
        // Create an HttpClient with automatic redirect handling
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)  // Handle redirects
                .build();

        // Build the HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .GET()
                .header("Accept", "application/json")  // Optional: set request headers
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print response details
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Headers: " + response.headers());

        // Check the response code
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to fetch data: HTTP code " + response.statusCode());
        }
    }

    public static void parseJsonResponse(String jsonResponse) {
        try {
            // The response is a JSON array
            JSONArray dataArray = new JSONArray(jsonResponse);

            // Print the JSON response (for debugging)
            System.out.println("Parsed JSON response:");
            System.out.println(dataArray.toString(4));  // Pretty print JSON

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject item = dataArray.getJSONObject(i);
                // Get fields from the JSON object
                int userId = item.getInt("userId");
                int id = item.getInt("id");
                String title = item.getString("title");
                String body = item.getString("body");
                System.out.println("User ID: " + userId + ", ID: " + id + ", Title: " + title + ", Body: " + body);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
