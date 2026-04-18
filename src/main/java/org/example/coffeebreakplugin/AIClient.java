package org.example.coffeebreakplugin;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * Provides AI-based logic for generating coffee break messages.
 *
 * This class integrates with two APIs (Ollama and Hugging Face) to request dynamic and personalized
 * coffee break suggestions based on user activity. If the APIs are unavailable, a default message is returned.
 */
public class AIClient {

    private static final String defaultResponse = "☕ Take a short break! Go and grab a cuppa coffee, do 10 squats or a moonwalk to stretch it out!";

    /**
     * Generates a break message based on user activity.
     *
     * Depending on whether an Ollama model or Hugging Face API key is available, it determines which service
     * to use for generating the message. Falls back to a default response when both fail.
     *
     * @param edits   The number of document edits the user has made.
     * @param minutes The number of minutes since the last break.
     * @return A personalized, fun coffee break message.
     */
    public static String getBreakMessage(int edits, long minutes) {
        // Get the API key from the settings
        String apiKey = SecureStorage.getApiKey();
        String ollamaModel = CoffeeBreakSettings.getFirstAvailableModel();
        boolean isOllama = true;
        String model = ollamaModel;
        // Check if API key is present
        if (apiKey != null && !apiKey.trim().isEmpty()) {
            isOllama = false;
            model = apiKey;
        }
        String prompt = """
                You are a friendly assistant inside a coding IDE.

                A developer is taking a break trigger.

                Stats:
                - minutes coding: %d
                - edits: %d

                Generate a short, extremely fun coffee break message (maximum 1 sentence).
                A punch line is essential! Also add a suggestion like 'do 10 squats', 'do a moonwalk', etc.
                """.formatted(minutes, edits);
        if(isOllama){
            System.out.println("trying Ollama, model: " + model);
            return getOllamaResponse(prompt, model);
        }
        System.out.println("trying Hugging face");
        return getHuggingFaceResponse(apiKey, prompt);
    }

    /**
     * Sends a prompt to the Ollama local AI model and retrieves a response.
     *
     * @param prompt   The text prompt describing the context of the request.
     * @param ollama   The name of the local Ollama model to use.
     * @return The generated response from the Ollama model, or a default message on failure.
     */
    private static String getOllamaResponse(String prompt, String ollama){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.writeValueAsString(Map.of(
                    "model", ollama,
                    "prompt", prompt,
                    "stream", false
            ));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response;
            try (HttpClient client = HttpClient.newHttpClient()) {

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }catch (Exception e) {
                e.printStackTrace();
                return defaultResponse;
            }

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

        } catch (Exception e) {
            e.printStackTrace();
            return defaultResponse;
        }
    }

    /**
     * Sends a prompt to Hugging Face's remote API and retrieves a response.
     *
     * @param apiKey The API key for accessing Hugging Face services.
     * @param prompt The text prompt describing the context of the request.
     * @return The generated response from Hugging Face, or a default message on failure.
     */
    public static String getHuggingFaceResponse(String apiKey, String prompt) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.writeValueAsString(Map.of(
                    "model", "meta-llama/Llama-3.1-8B-Instruct:cerebras",
                    "messages", List.of(Map.of("role", "user", "content", prompt)),
                    "max_tokens", 100
            ));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://router.huggingface.co/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response;
            try (HttpClient client = HttpClient.newHttpClient()) {

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }catch (Exception e) {
                e.printStackTrace();
                return defaultResponse;
            }

            System.out.println("HF RAW: " + response.body());

            JsonNode root = mapper.readTree(response.body());
            return root.get("choices").get(0).get("message").get("content").asText().replaceAll("^\"|\"$", "");

        } catch (Exception e) {
            e.printStackTrace();
            return defaultResponse;
        }
    }
}