package io.github.jonasfortes12.samples.debt.auth;

public class LoginService {

    /**
     * TODO DEBT2TEST-1: refactor this; legacy MD5 hashing is an ugly workaround and
     * password hashing should be redesigned around bcrypt (workFactor=12) or Argon2.
     */
    public boolean authenticate(String username, String password) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        String passwordHash = legacyMd5Hash(password);
        return validateHash(username, passwordHash);
    }

    private String legacyMd5Hash(String input) {
        // Simplified mock implementation
        return String.valueOf(input.hashCode());
    }

    private boolean validateHash(String username, String hash) {
        // Mock validation
        return hash.length() > 0;
    }
}
