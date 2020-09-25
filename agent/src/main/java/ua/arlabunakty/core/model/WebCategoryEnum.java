package ua.arlabunakty.core.model;

public enum WebCategoryEnum {
    REQUEST_OPERATION_TIME("requestOperationTime"),
    RESPONSE_BODY_LENGTH("responseBodyLength");

    private final String id;

    WebCategoryEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
