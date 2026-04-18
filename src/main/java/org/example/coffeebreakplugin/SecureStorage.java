package org.example.coffeebreakplugin;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.ide.passwordSafe.PasswordSafe;

/**
 * SecureStorage is responsible for securely storing and retrieving sensitive credentials
 * such as API keys. It uses IntelliJ's built-in PasswordSafe service to ensure
 * that the credentials are kept secure and encrypted.
 */
public class SecureStorage {

    // CredentialAttributes is used to define attributes for secure storage.
    // The "schemeName" here is "CoffeeBreakAPIKey", which serves as the unique key
    // to save and retrieve the API key in PasswordSafe.
    private static final CredentialAttributes ATTR =
            new CredentialAttributes("CoffeeBreakAPIKey");

    public static void saveApiKey(String key) {
        PasswordSafe.getInstance().setPassword(ATTR, key);
    }

    public static String getApiKey() {
        return PasswordSafe.getInstance().getPassword(ATTR);
    }
}