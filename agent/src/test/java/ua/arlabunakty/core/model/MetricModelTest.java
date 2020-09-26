package ua.arlabunakty.core.model;

import nl.jqno.equalsverifier.ConfiguredEqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class MetricModelTest {

    @Test
    void testEquals() {
        new ConfiguredEqualsVerifier()
                .suppress(Warning.NULL_FIELDS)
                .forClass(MetricModel.class)
                .verify();
    }
}