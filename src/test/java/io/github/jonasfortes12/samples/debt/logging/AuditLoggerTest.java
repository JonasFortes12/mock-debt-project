package io.github.jonasfortes12.samples.debt.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuditLoggerTest {

    private AuditLogger auditLogger;

    @BeforeEach
    void setUp() {
        auditLogger = new AuditLogger();
    }

    @Test
    void shouldRecordOneEntryPerRequest() {
        auditLogger.logRequest(new AuditLogger.Request("alice", "s3cr3t", "4111111111111111"));
        assertEquals(1, auditLogger.getLoggedEntries().size());
    }

    // TODO DEBT2TEST-6: document why this test asserts the plaintext password; explain that
    // it pins the current unredacted logging and must be inverted once redaction exists.
    @Test
    void currentlyLogsSensitiveFieldsInPlaintext() {
        auditLogger.logRequest(new AuditLogger.Request("alice", "s3cr3t", "4111111111111111"));
        String entry = auditLogger.getLoggedEntries().get(0);
        assertTrue(entry.contains("s3cr3t"));
        assertTrue(entry.contains("4111111111111111"));
    }
}
