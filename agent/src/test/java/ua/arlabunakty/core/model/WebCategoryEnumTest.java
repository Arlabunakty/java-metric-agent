package ua.arlabunakty.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class WebCategoryEnumTest {

    @Test
    void shouldHaveNonNullIds() {
        long nullIdsCount = Stream.of(WebCategoryEnum.values())
                .map(WebCategoryEnum::getId)
                .filter(Objects::isNull)
                .count();

        assertEquals(0, nullIdsCount, "WebCategoryEnum should have non-null ids");
    }

    @Test
    void shouldHaveUniqueIds() {
        long distinctCount = Stream.of(WebCategoryEnum.values())
                .map(WebCategoryEnum::getId)
                .distinct()
                .count();

        assertEquals(WebCategoryEnum.values().length, distinctCount,
                "WebCategoryEnum should have unique ids");
    }
}