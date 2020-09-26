package ua.arlabunakty.core.model;

import nl.jqno.equalsverifier.ConfiguredEqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class HistoryDataModelTest {

    @Test
    public void equalsContract() {
        new ConfiguredEqualsVerifier()
                .suppress(Warning.NULL_FIELDS)
                .forClass(HistoryDataModel.class)
                .verify();
    }
}