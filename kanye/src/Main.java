import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static Set<String> quotes = new HashSet<>();
    public static HttpRequest request;
    public static HttpClient httpClient;

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        request = HttpRequest.newBuilder()
                .uri(new URI("https://api.kanye.rest/"))
                .GET()
                .build();
        httpClient = HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build();

        System.out.println("Application started!");

        while (true) {
            String consoleInputString = scanner.nextLine();

            if (consoleInputString != null && consoleInputString.equals("next")) {
                String nextQuote = getNextQuote();
                while (quoteExists(nextQuote)) {
                    nextQuote = getNextQuote();
                }
                System.out.println(nextQuote);
                quotes.add(nextQuote);
            }

        }

    }

    public static boolean quoteExists(String quote) {
        for (String q : quotes) {
            if (q.equals(quote)) {
                return true;
            }
        }
        return false;
    }

    public static String getNextQuote() throws IOException, InterruptedException {
        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = httpResponse.body();
        if (responseBody != null && responseBody.startsWith("{\"quote\":")) {
            return responseBody.substring(responseBody.indexOf(":") + 1, responseBody.length() - 1);
        }
        return null;
    }

}
