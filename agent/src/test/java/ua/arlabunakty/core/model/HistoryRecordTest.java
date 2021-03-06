package ua.arlabunakty.core.model;

import nl.jqno.equalsverifier.ConfiguredEqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import ua.arlabunakty.core.domain.HistoryRecord;

class HistoryRecordTest {

    @Test
    void equalsContract() {
        new ConfiguredEqualsVerifier()
                .suppress(Warning.NULL_FIELDS)
                .forClass(HistoryRecord.class)
                .verify();
    }
}