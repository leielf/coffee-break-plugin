package org.example.coffeebreakplugin;

import com.intellij.openapi.options.Configurable;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CoffeeBreakConfigurable implements Configurable {

    private JPanel panel;       // Holds all components
    private JTextField apiKeyField; // Input field for the API key

    @Override
    public String getDisplayName() {
        return "Coffee Break Plugin";
    }

    @Override
    public JComponent createComponent() {
        // Main panel with GridBagLayout for proper alignment
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Add the API key label
        JLabel apiKeyLabel = new JLabel("Hugging Face API Key:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(apiKeyLabel, constraints);

        // Add the API key text field
        apiKeyField = new JTextField(30);  // Text field with 30-character width
        constraints.gridx = 1;
        panel.add(apiKeyField, constraints);

        return panel;
    }

    @Override
    public boolean isModified() {
        // Compares the input value with the saved value
        String savedApiKey = CoffeeBreakSettings.getInstance().getState().apiKey;
        String enteredApiKey = apiKeyField.getText();
        return !Objects.equals(savedApiKey, enteredApiKey);
    }

    @Override
    public void apply() {
        // Save the entered API key to CoffeeBreakSettings using SecureStorage
        SecureStorage.saveApiKey(apiKeyField.getText());

    }

    @Override
    public void reset() {
        // Load the saved API key into the text field
        String savedApiKey = CoffeeBreakSettings.getInstance().getState().apiKey;
        apiKeyField.setText(savedApiKey != null ? savedApiKey : "");
    }

    @Override
    public void disposeUIResources() {
        panel = null;
        apiKeyField = null;
    }
}
