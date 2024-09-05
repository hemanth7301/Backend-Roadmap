import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubActivityFetcher {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please re-run the code with valid argument");
            return;
        }
        String username = args[0];
        fetchGitHubActivity(username);
    }

    private static void fetchGitHubActivity(String username) {
        String urlString = "https://api.github.com/users/" + username + "/events/public";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlString)).GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int responseCode = response.statusCode();
            if (responseCode == 200) {
                parseAndDisplay(response.body());
            } else {
                System.out.println("Error: Unable to fetch the data for the user " + username);
            }

        } catch (Exception ex) {
            System.out.println("An error occurred " + ex.getMessage());
        }
    }

    private static void parseAndDisplay(String jsonResponse) {
        String[] events = jsonResponse.split("},\\{");
        for (String event : events) {
            if (event.contains("\"type\":\"PushEvent\"")) {
                String repoName = extractFieldFromEvent(event);
                System.out.println("Pushed to repository: " + repoName);
            } else if (event.contains("\"type\":\"PullRequestEvent\"")) {
                String repoName = extractFieldFromEvent(event);
                System.out.println("Opened a new PullRequest in: " + repoName);
            } else if (event.contains("\"type\":\"WatchEvent\"")) {
                String repoName = extractFieldFromEvent(event);
                System.out.println("Starred repository: " + repoName);
            } else if (event.contains("\"type\":\"MilestoneEvent\"")) {
                String repoName = extractFieldFromEvent(event);
                System.out.println("Created a MileStone in : " + repoName);
            }
        }
    }

    private static String extractFieldFromEvent(String event) {
        int fieldIndex = event.indexOf("\"" + "repo" + "\":");
        if (fieldIndex != -1) {
            int startIndex = event.indexOf("name\":\"", fieldIndex) + 7;
            int endIndex = event.indexOf("\"", startIndex);
            return event.substring(startIndex, endIndex);
        }
        return "Unknown";
    }
}