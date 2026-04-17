package org.example.coffeebreakplugin;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class AIClient {

//    private static String apiKey = "";

    public static String getBreakMessage(int edits, long minutes) {
        try {
            String prompt = """
                You are a friendly assistant inside a coding IDE.

                A developer is taking a break trigger.

                Stats:
                - minutes coding: %d
                - edits: %d

                Generate a short, extremely fun coffee break message (maximum 1 sentence). A punch line is essential!
                """.formatted(minutes, edits);

            ObjectMapper mapper = new ObjectMapper();

            String body = mapper.writeValueAsString(Map.of(
                    "model", "codellama",
                    "prompt", prompt,
                    "stream", false
            ));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

//            String body = mapper.writeValueAsString(Map.of(
//                    "model", "gpt-4.1-mini",
//                    "input", prompt
//            ));

//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("https://api.openai.com/v1/responses"))
//                    .header("Authorization", "Bearer " + API_KEY)
//                    .header("Content-Type", "application/json")
//                    .POST(HttpRequest.BodyPublishers.ofString(body))
//                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("OLLAMA RAW RESPONSE:");
            System.out.println(response.body());
            // Parse JSON response
            JsonNode jsonNode = mapper.readTree(response.body());

            JsonNode textNode = jsonNode.get("response");

            if (textNode == null) {
                System.out.println("Unexpected Ollama response: " + jsonNode);
                return "☕ Take a short coffee break!";
            }

            return textNode.asText();

//            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return "Take a short break! Go and grab a cuppa coffee, do 10 squats or a moonwalk to stretch it out!";
        }
    }
}