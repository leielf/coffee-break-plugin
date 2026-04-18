package org.example.coffeebreakplugin;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.ide.passwordSafe.PasswordSafe;

public class SecureStorage {

    private static final CredentialAttributes ATTR =
            new CredentialAttributes("CoffeeBreakAPIKey");

    public static void saveApiKey(String key) {
        PasswordSafe.getInstance().setPassword(ATTR, key);
    }

    public static String getApiKey() {
        return PasswordSafe.getInstance().getPassword(ATTR);
    }
}