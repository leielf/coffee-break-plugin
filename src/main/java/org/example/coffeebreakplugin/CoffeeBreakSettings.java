package org.example.coffeebreakplugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.openapi.components.Service;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Stores and manages persistent settings for the Coffee Break plugin.
 *
 * This class allows saving and retrieving plugin configurations, such as the API key,
 * using IntelliJ's PersistentStateComponent framework. It also includes helper methods
 * for retrieving available models using external commands.
 */

@Service
@State(
        name = "CoffeeBreakSettings",
        storages = @Storage("CoffeeBreakSettings.xml")
)
public final class CoffeeBreakSettings implements PersistentStateComponent<CoffeeBreakSettings.State> {

    /**
     * Represents the state that is persisted for CoffeeBreakSettings.
     * Currently, it includes a single field for storing the API key.
     */
    public static class State {
        public String apiKey = "";
    }

    private State state = new State();

    public static CoffeeBreakSettings getInstance() {
        return ApplicationManager.getApplication().getService(CoffeeBreakSettings.class);
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

    /**
     * Retrieves the first available model from the `ollama list` command.
     * @return The name of the first available model, or null if no models are found or an error occurs.
     */
    public static String getFirstAvailableModel() {
        try {
            // Run 'ollama list' command
            Process process = new ProcessBuilder("ollama", "list").start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                boolean skipHeader = true; // Skip the first line (headers)

                while ((line = reader.readLine()) != null) {
                    // Skip the header line
                    if (skipHeader) {
                        skipHeader = false;
                        continue;
                    }

                    // Return the first non-empty line
                    if (!line.isBlank()) {
                        // Extract the model name by splitting and trimming the line
                        String[] parts = line.trim().split("\\s+");
                        if (parts.length > 0) {
                            return parts[0];
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error while executing 'ollama list': " + e.getMessage());
        }
        return "";
    }
}