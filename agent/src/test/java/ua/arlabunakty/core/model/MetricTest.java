package ua.arlabunakty.core.model;

import nl.jqno.equalsverifier.ConfiguredEqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import ua.arlabunakty.core.domain.Metric;

class MetricTest {

    @Test
    void testEquals() {
        new ConfiguredEqualsVerifier()
                .suppress(Warning.NULL_FIELDS)
                .forClass(Metric.class)
                .verify();
    }
}