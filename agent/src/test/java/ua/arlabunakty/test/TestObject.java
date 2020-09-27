package ua.arlabunakty.test;

public class TestObject {

    private final String value;

    public TestObject(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
