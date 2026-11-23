package io.github.jonasfortes12.samples.debt.logging;

import java.util.ArrayList;
import java.util.List;

public class AuditLogger {

    private final List<String> sink = new ArrayList<>();

    /**
     * FIXME DEBT2TEST-6: bug, logged entries contain the raw password and card number in plaintext.
     * The request is logged via toString() with no redaction step.
     */
    public void logRequest(Request request) {
        sink.add(request.toString());
    }

    public List<String> getLoggedEntries() {
        return sink;
    }

    public static final class Request {
        private final String username;
        private final String password;
        private final String cardNumber;

        public Request(String username, String password, String cardNumber) {
            this.username = username;
            this.password = password;
            this.cardNumber = cardNumber;
        }

        public String username() { return username; }
        public String password() { return password; }
        public String cardNumber() { return cardNumber; }

        @Override
        public String toString() {
            return "Request{username=" + username + ", password=" + password
                    + ", cardNumber=" + cardNumber + "}";
        }
    }
}
