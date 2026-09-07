package io.github.jonasfortes12.samples.debt.auth;

public class LoginService {

    /**
     * TODO: DEBT2TEST-1
     * This authentication mechanism uses MD5 hashing which is deprecated.
     * MD5 is cryptographically broken and should not be used for password hashing.
     * Migration target: bcrypt with workFactor=12 or Argon2.
     *
     * Legacy code from 2015. Performance impact: ~15% slower on login attempts.
     * Blocks user experience improvements until resolved.
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
